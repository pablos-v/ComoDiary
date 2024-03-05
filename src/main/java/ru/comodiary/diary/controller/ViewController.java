package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.TaskStatus;
import ru.comodiary.diary.service.TaskService;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class ViewController {

    private final TaskService service;

    // по умолчанию выводит представление месяц с текущей даты, если в форме была введена дата-то с указанной
    @GetMapping("/")
    public String viewMonth(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("month", service.getAllTasksForMonth(date));
        model.addAttribute("monthName", service.getMonthName(date));
        model.addAttribute("expired", service.getAllExpiredTasks());
        return "month";
    }

    @GetMapping("/3_days")
    public String viewFourDays(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("threeDays", service.getAllTasksThreeDays(date));
        model.addAttribute("expired", service.getAllExpiredTasks());
        return "3_days";
    }

    @GetMapping("/day")
    public String viewDay(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("day", service.getAllDayTasks(date));
        model.addAttribute("dayName", service.getDayName(date));
        model.addAttribute("expired", service.getAllExpiredTasks());
        return "day";
    }

    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "search") String search) {
        model.addAttribute("tasks", service.getAllTasksBySearch(search));
        model.addAttribute("expired", service.getAllExpiredTasks());
        return "list";
    }

    @GetMapping("/expired")
    public String viewListOfExpiredTasks(Model model) {
        model.addAttribute("tasks", service.getAllExpiredTasks());
        return "list";
    }

    @GetMapping("/task/{id}")
    public String viewTask(Model model, @PathVariable Long id) {
        model.addAttribute("task", service.getTaskById(id));
        model.addAttribute("expired", service.getAllExpiredTasks());
        return "task";
    }

    // for new task
    @GetMapping("/task")
    public String viewAddTask(Model model) {
        Task task = new Task();
        task.setExpireDate(LocalDate.now());
        task.setStatus(TaskStatus.NOT_COMPLETED);
        model.addAttribute("task", task);
        return "task";
    }
}
