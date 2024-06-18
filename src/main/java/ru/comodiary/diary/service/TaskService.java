package ru.comodiary.diary.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * Формирует на основе даты объект DTO Month, содержащий все задачи на месяц.
     * @param date строковое представление даты
     * @return объект DTO Month
     */
    public Month getAllTasksForMonth(String date) {
        LocalDate startDate = Util.convertStringToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        // нужно для вывода номеров дат с корректного дня недели
        int diffToMonday = startDate.getDayOfWeek().compareTo(DayOfWeek.MONDAY);

        List<Task> allTasksOfMonth = repository.findByExpireDateBetween(startDate, lastDate);

        return new Month(allTasksOfMonth, diffToMonday, lastDate);
    }

    /**
     *  Формирует на основе даты объект DTO ThreeDays, содержащий все задачи на три дня, начиная с указанной даты.
     * @param date строковое представление даты
     * @return объект DTO ThreeDays
     */
    public ThreeDays getAllTasksThreeDays(String date) {
        LocalDate firstDate = Util.convertStringToLocalDate(date);
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            days.add(new Day(repository.findByExpireDate(firstDate.plusDays(i)), firstDate.plusDays(i)));
        }
        return new ThreeDays(days, firstDate);
    }

    /**
     * Формирует объект DTO Day, содержащий все задачи на указанную дату.
     * @param date строковое представление даты
     * @return объект DTO Day
     */
    public Day getAllDayTasks(String date) {
        LocalDate localDate = Util.convertStringToLocalDate(date);
        return new Day(repository.findByExpireDate(localDate), localDate);
    }

    /**
     * Получает объект Task по его id
     * @param id id задачи
     * @return объект Task
     */
    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id = %d is not found", id)));
    }

    /**
     * На основе переданных данных создаёт задачу и сохраняет её в БД.
     * @param title заголовок задачи
     * @param description описание задачи
     * @param expireDate дата задачи
     * @param status статус задачи
     */
    public void addTask(String title, String description, String expireDate, String status) {
        repository.save(new Task(title, description, expireDate, status));
    }

    /**
     * Удаляет задачу по id
     * @param id id задачи
     * @return объект удалённой задачи
     */
    public Task deleteTaskById(Long id) {
        Task task = getTaskById(id);
        repository.deleteById(id);
        return task;
    }

    /**
     * Формирует список задач, содержащих поисковый запрос в заголовке или описании задачи, позже указанной даты.
     * @param query поисковый запрос
     * @param date строковое представление даты
     * @return список задач
     */
    public List<Task> getAllTasksBySearchAndDate(String query, String date) {
        LocalDate localDate = Util.convertStringToLocalDate(date);
        List<Task> taskList = repository.
                findByTitleContainingOrDescriptionContainingAndExpireDateGreaterThan(query, query, localDate);
        if (taskList.size() > 1) taskList.sort(Comparator.comparing(Task::getExpireDate));
        return taskList;
    }

    /**
     * Формирует список просроченных задач, отсортированный по дате
     * @return список просроченных задач
     */
    @Transactional
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

    /**
     * Меняет статус задачи и сохраняет обновлённую задачу в БД
     * @param id id задачи
     */
    public void changeTaskStatusById(String id) {
        Task task = getTaskById(Long.valueOf(id));
        task.setStatus(task.getStatus() == TaskStatus.COMPLETED ? TaskStatus.NOT_COMPLETED : TaskStatus.COMPLETED);
        repository.save(task);
    }

    /**
     * Изменение задачи на основе переданных параметров.
     * @param id id задачи
     * @param title заголовок задачи
     * @param description описание задачи
     * @param expireDate дата задачи
     * @param status статус задачи
     */
    public void updateTask(Long id, String title, String description, String expireDate, String status) {
        Task task = getTaskById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setExpireDate(Util.convertStringToLocalDate(expireDate));
        if (!status.equals("no")) task.setStatus(Util.stringToStatus(status));
        repository.save(task);
    }
}
