package ru.yandex.practicum.analyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.analyzer.config.KafkaConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HubEventProcessor implements Runnable {

    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private final KafkaConsumer<String, HubEventAvro> consumer;
    private final List<String> topics;
    private final Duration pollTimeout;
    private final ScenarioService scenarioService;

    public HubEventProcessor(KafkaConfig config, ScenarioService scenarioService) {
        final KafkaConfig.ConsumerConfig consumerConfig = config.getConsumers().get(this.getClass().getSimpleName());
        this.consumer = new KafkaConsumer<>(consumerConfig.getProperties());
        this.topics = consumerConfig.getTopics();
        this.pollTimeout = consumerConfig.getPollTimeout();
        this.scenarioService = scenarioService;

        // регистрируем хук, в котором вызываем метод wakeup.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Сработал хук на завершение JVM. Прерываю работу консьюмера.");
            consumer.wakeup();
        }));
    }

    @Override
    public void run() {
        try{
            log.trace("Подписываемся на топики {}", topics);
            consumer.subscribe(topics);
            // цикл опроса
            while (true) {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(pollTimeout);
                int count = 0;
                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    log.trace("Обработка сообщения от хаба {} из партиции {} с офсетом {}.",
                            record.key(), record.partition(), record.offset());
                    // обрабатываем очередную запись
                    handleRecord(record.value());
                    // фиксируем оффсеты обработанных записей, если нужно
                    manageOffsets(record, count, consumer);
                    count++;
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignores) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Ошибка во время обработки сценариев от хабов", e);
        } finally {
            try {
                // здесь нужно вызвать метод консьюмера для фиксиции смещений
                consumer.commitSync(currentOffsets);
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
            }
        }

    }

    private static void manageOffsets(ConsumerRecord<String, HubEventAvro> record, int count,
                                      KafkaConsumer<String, HubEventAvro> consumer) {
        // обновляем текущий оффсет для топика-партиции
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );
        if(count % 100 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if(exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }

    private void handleRecord(HubEventAvro hubEventAvro) {
        try {
            String hubId = hubEventAvro.getHubId();
            switch (hubEventAvro.getPayload()) {
                case DeviceAddedEventAvro dae -> scenarioService.handleDeviceAdded(hubId, dae);
                case DeviceRemovedEventAvro dre -> scenarioService.handleDeviceRemoved(hubId, dre);
                case ScenarioAddedEventAvro sae -> scenarioService.handleScenarioAdded(hubId, sae);
                case ScenarioRemovedEventAvro sre -> scenarioService.handleScenarioRemoved(hubId, sre);
                default -> log.warn("Неизвестный тип события: {}", hubEventAvro);
            }
        } catch (Exception e) {
            log.error("Ошибка обработки события для хаба {}", hubEventAvro.getHubId(), e);
        }
    }
}