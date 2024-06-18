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
    private static final String REDIRECT_DAY_DATE = "redirect:/day?date=";

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

        service.addTask(title, description, expireDate, status);
        return REDIRECT_DAY_DATE + expireDate;
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
    @PutMapping("/task/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("expireDate") String expireDate,
                                   @RequestParam(name = "status", defaultValue = "no") String status) {

        service.updateTask(id, title, description, expireDate, status);
        return REDIRECT_DAY_DATE  + expireDate;
    }

    /**
     * Удаление задачи (Delete) по ID и редирект.
     * @param id Идентификатор задачи
     * @return Редирект на день с датой = expireDate удалённой задачи
     */
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {

        return REDIRECT_DAY_DATE + service.deleteTaskById(id).getExpireDate().toString();
    }

    /**
     * Смена статуса задачи
     * @param id Идентификатор задачи
     * @param whereTo адрес для переадресации
     * @return Редирект по адресу, заданному в whereTo
     */
    @PutMapping("/change-status")
    public String changeTaskStatus(@RequestParam("id") String id, @RequestParam("whereTo") String whereTo) {
        service.changeTaskStatusById(id);
        return "redirect:" + whereTo;
    }
}
