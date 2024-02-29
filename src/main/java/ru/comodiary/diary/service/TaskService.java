package ru.comodiary.diary.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private TaskRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Task> getAllTasksForMonth(String date) {
        // парсим стринг дату и приводим форматтером к LocalDate
        LocalDate startDate = LocalDate.parse(date, formatter)
                // берём первый день
                .with(TemporalAdjusters.firstDayOfMonth());

        // берём последний день
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        return repository.findByExpireDateBetween(startDate, lastDate);
    }

    public List<Task> getAllTasks4Days(String date) {
        LocalDate startDate = LocalDate.parse(date, formatter);

        // берём последний день
        LocalDate lastDate = startDate.plusDays(4);
        // between берёт значение включительно или нет? если да то startDate.plusDays(3)
        return repository.findByExpireDateBetween(startDate, lastDate);
    }

    public List<Task> getAllDayTasks(String date) {
        LocalDate expireDate = LocalDate.parse(date, formatter);
        return repository.findByExpireDate(expireDate);
    }

    public List<Task> getAllTasksFrom(String date) {
        LocalDate expireDate = LocalDate.parse(date, formatter);
        return repository.findByExpireDateGreaterThanEqual(expireDate);
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id = %d is not found", id)));
    }

    // TODO тестить как с ним работает PUT
    public Task addOrUpdateTask(Task task) {
        return repository.save(task);
    }

    public Task deleteTaskById(long id) {
        // не нужно проверять на экзист, это сделает getTaskById(id)
        Task result = getTaskById(id);
        repository.deleteById(id);
        return result;
    }
}
