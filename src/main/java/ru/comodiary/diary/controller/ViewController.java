package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.comodiary.diary.service.TaskService;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class ViewController {

    TaskService service;

    // по умолчанию выводит представление месяц с текущей даты, если в форме была введена дата-то с указанной
    @GetMapping("/")
    public String viewMonth(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("tasks", service.getAllTasksForMonth(date));
        return "month";
    }

    @GetMapping("/4_days")
    public String viewFourDays(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("tasks", service.getAllTasksSomeDays(date));
        return "4_days";
    }

    @GetMapping("/day")
    public String viewDay(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("tasks", service.getAllDayTasks(date));
        return "day";
    }

    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("tasks", service.getAllTasksFromDate(date));
        return "list";
    }

    @GetMapping("/task/{id}")
    public String viewTask(Model model, @PathVariable long id) {
        model.addAttribute("task", service.getTaskById(id));
        return "task";
    }
}
