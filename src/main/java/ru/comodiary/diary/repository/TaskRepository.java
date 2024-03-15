package ru.comodiary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByExpireDateLessThanAndStatusEquals(LocalDate date, TaskStatus status);

    List<Task> findByExpireDateGreaterThanEqualAndStatusEquals(LocalDate date, TaskStatus status);

    List<Task> findByExpireDateBetween(LocalDate startDate, LocalDate lastDate);

    List<Task> findByExpireDate(LocalDate date);

    // для фильтра
    List<Task> findByStatus(TaskStatus taskStatus);

    // поиск
    List<Task> findByTitleContainingOrDescriptionContainingAndExpireDateGreaterThan(String queryForTitle, String queryForDescription, LocalDate date);


}
