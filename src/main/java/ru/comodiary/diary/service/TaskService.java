package ru.comodiary.diary.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.*;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Основная бизнес-логика
 */
@RequiredArgsConstructor
@Service
public class TaskService {

    public static final String DAY_WITH_DATE = "redirect:/day?date=";
    private final TaskRepository repository;

    /**
     * Создание демо-задачи при первом запуске
     */
    @PostConstruct
    private void createDemoTask() {
        if (repository.findAll().isEmpty()) {
            Task task = new Task("DEMO Task Title", "DEMO Task Description", LocalDate.now());
            repository.save(task);
        }
    }

    public Month getAllTasksForMonth(String date) {
        LocalDate startDate = Util.convertStringToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        // нужна для вывода номеров дат с корректного дня недели
        int diffToMonday = startDate.getDayOfWeek().compareTo(DayOfWeek.MONDAY);

        List<Task> allTasksOfMonth = repository.findByExpireDateBetween(startDate, lastDate);

        return new Month(allTasksOfMonth, diffToMonday, lastDate);
    }

    public ThreeDays getAllTasksThreeDays(String date) {
        LocalDate firstDate = Util.convertStringToLocalDate(date);
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            days.add(new Day(repository.findByExpireDate(firstDate.plusDays(i)), firstDate.plusDays(i)));
        }
        return new ThreeDays(days, firstDate);
    }

    public Day getAllDayTasks(String date) {
        LocalDate localDate = Util.convertStringToLocalDate(date);
        return new Day(repository.findByExpireDate(localDate), localDate);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id = %d is not found", id)));
    }

    public String addTaskAndRedirect(String title, String description, String expireDate, String status) {
        repository.save(new Task(title, description, expireDate, status));
        return DAY_WITH_DATE + expireDate;
    }

    public String deleteTaskById(Long id) {
        String date = getTaskById(id).getExpireDate().toString();
        repository.deleteById(id);

        return DAY_WITH_DATE + date;
    }

    public List<Task> getAllTasksBySearchAndDate(String query, String date) {
        LocalDate localDate = Util.convertStringToLocalDate(date);
        List<Task> taskList = repository.
                findByTitleContainingOrDescriptionContainingAndExpireDateGreaterThan(query, query, localDate);
        if (taskList.size() > 1) taskList.sort(Comparator.comparing(Task::getExpireDate));
        return taskList;
    }

    public List<Task> getAllExpiredTasks() {
        updateTasks();
        List<Task> tasks = repository.findByStatus(TaskStatus.EXPIRED);
        if (tasks.size() > 1) tasks.sort(Comparator.comparing(Task::getExpireDate));
        return tasks;
    }

    /**
     * Актуализирует статусы невыполненных просроченных задач и просроченных задач с изменённой датой
     */
    private void updateTasks() {
        // обновить статусы всем просроченным невыполненным задачам
        List<Task> tasks = repository.findByExpireDateLessThanAndStatusEquals(LocalDate.now(), TaskStatus.NOT_COMPLETED);
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.EXPIRED);
        }
        repository.saveAll(tasks);

        // обновить статусы при изменении даты просроченной задачи
        tasks = repository.findByExpireDateGreaterThanEqualAndStatusEquals(LocalDate.now(), TaskStatus.EXPIRED);
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.NOT_COMPLETED);
        }
        repository.saveAll(tasks);
    }

    public String changeStatusAndRedirect(String id, String whereTo) {
        Task task = getTaskById(Long.valueOf(id));
        task.setStatus(task.getStatus() == TaskStatus.COMPLETED ? TaskStatus.NOT_COMPLETED : TaskStatus.COMPLETED);
        repository.save(task);

        return "redirect:" + whereTo;
    }

    public String updateTaskAndRedirect(Long id, String title, String description, String expireDate, String status) {
        Task task = getTaskById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setExpireDate(Util.convertStringToLocalDate(expireDate));
        if (!status.equals("no")) task.setStatus(Util.stringToStatus(status));
        repository.save(task);
        return DAY_WITH_DATE + expireDate;
    }
}
