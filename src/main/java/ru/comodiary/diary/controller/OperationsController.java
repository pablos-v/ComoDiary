package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.comodiary.diary.service.TaskService;

/**
 * Контроллер, отвечающий за операции удаления, создания и изменения задач
 */
@Controller
@AllArgsConstructor
public class OperationsController {

    private final TaskService service;

    /**
     * Создание новой задачи (Create)
     * @param title Заголовок
     * @param description Описание
     * @param expireDate Дата
     * @param status Статус
     * @return Редирект на день с датой = expireDate
     */
    @PostMapping("/task")
    public String addNewTask(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam("status") String status) {

        return service.addTaskAndRedirect(title, description, expireDate, status);
    }

    /**
     * Изменение существующей задачи (Update)
     * @param id Идентификатор задачи
     * @param title Заголовок
     * @param description Описание
     * @param expireDate Дата
     * @param status Статус
     * @return Редирект на день с датой = expireDate
     */
    @PostMapping("/task/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam(name = "status", defaultValue = "no") String status) {

        return service.updateTaskAndRedirect(id, title, description, expireDate, status);
    }

    /**
     * Удаление задачи (Delete)
     * @param id Идентификатор задачи
     * @return Редирект на день с датой = expireDate удалённой задачи
     */
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {

        return service.deleteTaskById(id);
    }

    /**
     * Смена статуса задачи
     * @param id Идентификатор задачи
     * @param whereTo адрес для переадресации
     * @return Редирект по адресу, заданному в whereTo
     */
    @PostMapping("/change-status")
    public String changeTaskStatus(@RequestParam("id") String id,
                                         @RequestParam("whereTo") String whereTo) {

        return service.changeStatusAndRedirect(id, whereTo);
    }
}
