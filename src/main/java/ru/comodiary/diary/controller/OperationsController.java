package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.comodiary.diary.service.TaskService;

@Controller
@AllArgsConstructor
public class OperationsController {

    private final TaskService service;

    // создать новую
    @PostMapping("/task")
    public String addNewTask(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam("status") String status) {

        return service.addTaskAndRedirect(title, description, expireDate, status);
    }

    // фактически это PUT, но HTML формы поддерживают только 2 метода GET и POST... ссыль в литературе есть
    @PostMapping("/task/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam(name = "status", defaultValue = "no") String status) {

        return service.updateTaskAndRedirect(id, title, description, expireDate, status);
    }

    // удаляет задачу и редиректит обратно в день
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {

        return service.deleteTaskById(id);
    }

    // смена статуса задачи и редирект смотря куда надо
    @PostMapping("/change-status")
    public String changeTaskStatus(@RequestParam("id") String id,
                                         @RequestParam("whereTo") String whereTo) {

        return service.changeStatusAndRedirect(id, whereTo);
    }
}
