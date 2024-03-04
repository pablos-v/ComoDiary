package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.service.TaskService;

@Controller
@AllArgsConstructor
public class PostPutDeleteController {

    private final TaskService service;

    @PostMapping("/task")
    public String addNewTask(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("expireDate") String expireDate,
                             @RequestParam("status") String status,
                             Model model) {
        Task task = new Task(title, description, expireDate, status);
        service.addOrUpdateTask(task);
        model.addAttribute("day", service.getAllDayTasks(task.getExpireDate().toString()));
        return "day";
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
