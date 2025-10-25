package ru.yandex.practicum.analyzer.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.analyzer.dal.model.Condition;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}