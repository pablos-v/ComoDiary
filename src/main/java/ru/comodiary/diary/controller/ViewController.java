package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.comodiary.diary.model.Util;
import ru.comodiary.diary.service.TaskService;

@Controller
@AllArgsConstructor
public class ViewController {

    private final TaskService service;

    // по умолчанию выводит представление месяц с текущей даты, если в форме была введена дата-то с указанной
    @GetMapping("/")
    public String viewMonth(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("month", service.getAllTasksForMonth(date));
        model.addAttribute("monthName", Util.getMonthName(date));
        return "month";
    }

    @GetMapping("/3_days")
    public String viewThreeDays(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("threeDays", service.getAllTasksThreeDays(date));
        return "3_days";
    }

    @GetMapping("/day")
    public String viewDay(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("day", service.getAllDayTasks(date));
        return "day";
    }

    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("tasks", service.getAllTasksBySearchAndDate(search, date));
        model.addAttribute("urlAddress", "/list?search=" + search);
        return "list";
    }

    @GetMapping("/expired")
    public String viewListOfExpiredTasks(Model model) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("tasks", service.updateAndGetAllExpiredTasks());
        model.addAttribute("urlAddress", "/expired");
        return "list";
    }

    @GetMapping("/task/{id}")
    public String viewTask(Model model, @PathVariable Long id) {
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        model.addAttribute("task", service.getTaskById(id));
        return "task";
    }


    @GetMapping("/task")
    public String viewNewTask(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("task", Util.prepareTask(date));
        model.addAttribute("expired", service.updateAndGetAllExpiredTasks());
        return "task";
    }
}
