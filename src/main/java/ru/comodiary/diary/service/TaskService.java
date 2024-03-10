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

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;
    @PostConstruct
    private void createDemoTask(){
        if (repository.findAll().isEmpty()){
            Task task = new Task("DEMO Task Title", "DEMO Task Description", LocalDate.now());
            repository.save(task);
        }
    }

    public Month getAllTasksForMonth(String date) {
        // берём первый день
        LocalDate startDate = Util.convertStringToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        // берём последний день
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        // нужно для вывода номеров дат с корректного дня недели
        DayOfWeek startDayOfWeek = startDate.getDayOfWeek();
        int diffToMonday = startDayOfWeek.compareTo(DayOfWeek.MONDAY);

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

    public void addOrUpdateTask(Task task) {
        repository.save(task);
    }

    public Task deleteTaskById(Long id) {
        // не нужно проверять на экзист, это сделает getTaskById(id)
        Task result = getTaskById(id);
        repository.deleteById(id);
        return result;
    }

    public List<Task> getAllTasksBySearch(String search) {
        List<Task> taskList = repository.findByTitleContainingOrDescriptionContaining(search, search);
        taskList.sort(Comparator.comparing(Task::getExpireDate));
        return taskList;
    }

    public List<Task> updateAndGetAllExpiredTasks() {
        // найти все невып таски младше даты
        List<Task> tasks = repository.findByExpireDateLessThanAndStatusEquals(LocalDate.now(), TaskStatus.NOT_COMPLETED);
        // всем менять статус
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.EXPIRED);
            repository.saveAll(tasks);
        }
        // найти среди просроченных таски старше или равные даты
        tasks = repository.findByExpireDateGreaterThanEqualAndStatusEquals(LocalDate.now(), TaskStatus.EXPIRED);
        // менять статусы
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.NOT_COMPLETED);
            repository.saveAll(tasks);
        }
        return repository.findByStatus(TaskStatus.EXPIRED);
    }

    public Task changeStatus(Long id) {
        Task task = getTaskById(id);
        task.setStatus(task.getStatus() == TaskStatus.COMPLETED ? TaskStatus.NOT_COMPLETED : TaskStatus.COMPLETED);
        repository.save(task);
        return task;
    }

}
