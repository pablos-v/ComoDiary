package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.service.TaskService;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class Crud {

    TaskService service;

    // по умолчанию выводит представление месяц с текущей даты, если в форме была введена дата-то с указанной
    @GetMapping("/")
    public String viewMonth(Model model, @RequestParam(value = "date"
            , defaultValue = "new LocalDate().toString()") String date) {
        model.addAttribute("tasks", service.getAllTasksForMonth(date));
        return "month";
    }

    @GetMapping("/4_days")
    public String viewFourDays(Model model, @RequestParam(value = "date"
            , defaultValue = "new LocalDate().toString()") String date) {
        model.addAttribute("tasks", service.getAllTasks4Days(date));
        return "4_days";
    }

    @GetMapping("/day")
    public String viewDay(Model model, @RequestParam(value = "date"
            , defaultValue = "new LocalDate().toString()") String date) {
        model.addAttribute("tasks", service.getAllDayTasks(date));
        return "day";
    }
    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "date"
            , defaultValue = "new LocalDate().toString()") String date) {
        model.addAttribute("tasks", service.getAllTasksFrom(date));
        return "list";
    }


    // TODO куда потом редирект? сунуть в модель то что вернулось и открыть таск?
    // TODO нужны ли ResponseEntity? как их навесить?
    @PostMapping("/")
    public void addNewTask(@RequestBody Task task) {
        service.addOrUpdateTask(task);
    }

    // TODO  куда потом редирект? сунуть в модель то что вернулось и открыть таск?
    @PutMapping("/task/{id}")
    public void updateTask(@PathVariable long id) {
        service.addOrUpdateTask(service.getTaskById(id));
    }

    // TODO сообщить что задача с тайтлом таким-то удалена и редирект куда?
    @DeleteMapping("/task/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteTaskById(id);
    }
}
