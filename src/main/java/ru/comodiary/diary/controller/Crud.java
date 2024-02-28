package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.service.TaskService;

@Controller
@AllArgsConstructor
public class Crud {

    TaskService service;

    // месяц - представление по умолчанию
    // TODO надо понять как фильтровать даты
    @GetMapping("/")
    public String viewAllMonth(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        return "month";
    }

    @GetMapping("/4_days")
    public String viewFourDays(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        return "4_days";
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
