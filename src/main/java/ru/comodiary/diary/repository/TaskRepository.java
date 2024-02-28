package ru.comodiary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.comodiary.diary.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findByExpireDateGreaterThanEqual(LocalDate expireDate);

    // поиск
    public List<Task> findDistinctByTitleContainingOrDescriptionContaining(String search);


}
