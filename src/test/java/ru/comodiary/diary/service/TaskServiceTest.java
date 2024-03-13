package ru.comodiary.diary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.comodiary.diary.model.Day;
import ru.comodiary.diary.model.Month;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.ThreeDays;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService taskService;
    List<Task> allTasks;
    Task task;

    @BeforeEach
    void init() {
        task = new Task("123", "456", LocalDate.of(2024, 5, 22));
        allTasks = List.of(task);
    }

    @Test
    void getAllTasksForMonth() {
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate lastDate = LocalDate.of(2024, 5, 31);
        when(repository.findByExpireDateBetween(startDate, lastDate)).thenReturn(allTasks);

        Month expected = new Month(allTasks, 2, lastDate);
        Month response = taskService.getAllTasksForMonth("2024-05-22");

        assertEquals(expected, response);
    }

    @Test
    void getAllTasksThreeDays() {
        LocalDate firstDate = LocalDate.of(2024, 5, 22);
        Day day1 = new Day(allTasks, firstDate);
        Day day2 = new Day(new ArrayList<>(), firstDate.plusDays(1));
        Day day3 = new Day(new ArrayList<>(), firstDate.plusDays(2));
        List<Day> days = List.of(day1, day2, day3);

        when(repository.findByExpireDate(firstDate)).thenReturn(allTasks);

        ThreeDays expected = new ThreeDays(days, firstDate);
        ThreeDays response = taskService.getAllTasksThreeDays("2024-05-22");

        assertEquals(expected, response);
    }

    @Test
    void getAllDayTasks() {
        LocalDate date = LocalDate.of(2024, 5, 22);

        when(repository.findByExpireDate(date)).thenReturn(allTasks);

        Day expected = new Day(allTasks, date);
        Day response = taskService.getAllDayTasks("2024-05-22");

        assertEquals(expected, response);
    }
    @Test
    void getTaskById() {
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));
        assertEquals("Task with id = 1 is not found", exception.getMessage());
    }

    @Test
    void addTaskAndRedirect() {
        when(repository.save(task)).thenReturn(task);

        String expected = "redirect:/day?date=2024-05-22";
        String response = taskService.addTaskAndRedirect(task.getTitle(),task.getDescription(),"2024-05-22", task.getStatus().toString());

        assertEquals(expected, response);
    }


    @Test
    void deleteTaskById() {
    }

    @Test
    void getAllTasksBySearchAndDate() {
    }

    @Test
    void updateAndGetAllExpiredTasks() {
    }

    @Test
    void changeStatusAndRedirect() {
    }

    @Test
    void updateTaskAndRedirect() {
    }
}