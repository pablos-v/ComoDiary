package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.Util;
import ru.comodiary.diary.service.TaskService;

@Controller
@AllArgsConstructor
public class OperationsController {

    private final TaskService service;

    // создать новую
    @PostMapping("/task")
    public ModelAndView addNewTask(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam("status") String status) {
        Task task = new Task(title, description, expireDate, status);
        service.addOrUpdateTask(task);

        return new ModelAndView("redirect:" + "/day?date=" + expireDate);
    }

    // фактически это PUT, но HTML формы поддерживают только 2 метода GET и POST... ссыль в литературе есть
    @PostMapping("/task/{id}")
    public ModelAndView updateTask(@PathVariable Long id, @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam(name = "status", defaultValue = "no") String status) {

        service.updateTask(id, title, description, expireDate, status);

        return new ModelAndView("redirect:" + "/day?date=" + expireDate);
    }

    // удаляет задачу и редиректит обратно в день
    @PostMapping("/delete/{id}")
    public ModelAndView deleteById(@PathVariable Long id) {
        Task task = service.deleteTaskById(id);

        return new ModelAndView("redirect:" + "/day?date=" + task.getExpireDate());
    }

    // смена статуса задачи и редирект смотря куда надо
    @PostMapping("/change-status")
    public ModelAndView changeTaskStatus(@RequestParam("id") String id,
                                         @RequestParam("whereTo") String whereTo) {
        Task task = service.changeStatus(Long.valueOf(id));
        if (whereTo.equals("day")) return new ModelAndView("redirect:" + "/day?date=" + task.getExpireDate());
        else return new ModelAndView("redirect:" + whereTo);
    }
}
