package ru.comodiary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByExpireDateGreaterThanEqual(LocalDate date);

    List<Task> findByExpireDateBetween(LocalDate startDate, LocalDate lastDate);

    List<Task> findByExpireDate(LocalDate date);

    // для фильтра
    List<Task> findByStatus(TaskStatus taskStatus);

    // поиск
    List<Task> findByTitleContainingOrDescriptionContaining(String search, String search1);


}
