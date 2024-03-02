package ru.comodiary.diary.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.TaskStatus;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class TaskService {
    final int DAYS_VIEW = 4;

    private TaskRepository repository;

    @PostConstruct
    private void fakeData() {
        Task one = new Task("first", "ftest", LocalDate.of(2024, 3, 1), TaskStatus.NOT_COMPLETED);
        Task two = new Task("second", "stest", LocalDate.of(2024, 3, 2), TaskStatus.NOT_COMPLETED);
        Task three = new Task("third", "thtest", LocalDate.of(2024, 3, 31), TaskStatus.NOT_COMPLETED);
        Task four = new Task("four", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        addOrUpdateTask(one);
        addOrUpdateTask(two);
        addOrUpdateTask(three);
        addOrUpdateTask(four);
    }


    public List<Task> getAllTasksForMonth(String date) {
        // берём первый день
        LocalDate startDate = convertStringToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        // берём последний день
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        return repository.findByExpireDateBetween(startDate, lastDate);
    }

    public List<Task> getAllTasksSomeDays(String date) {
        LocalDate startDate = convertStringToLocalDate(date);
        return repository.findByExpireDateBetween(startDate, startDate.plusDays(DAYS_VIEW));
    }

    public List<Task> getAllDayTasks(String date) {
        return repository.findByExpireDate(convertStringToLocalDate(date));
    }

    public List<Task> getAllTasksFromDate(String date) {
        return repository.findByExpireDateGreaterThanEqual(convertStringToLocalDate(date));
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

    private LocalDate convertStringToLocalDate(String date) {
        if (Objects.equals(date, "nowDate")) return LocalDate.now();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
