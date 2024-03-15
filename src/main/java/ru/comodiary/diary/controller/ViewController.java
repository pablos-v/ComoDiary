package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.Util;
import ru.comodiary.diary.service.TaskService;

import java.util.List;

/**
 * Контроллер, отвечающий за отдачу корректных шаблонов визуальных представлений
 */
@Controller
@AllArgsConstructor
public class ViewController {

    public static final String EXPIRED = "expired";
    private final TaskService service;

    /**
     * Представление "месяц"
     * @param model Модель данных - класс, предоставляемый Spring
     * @param date Дата, передаваемая с клиента
     * @return Отдаёт представление "месяц" за месяц текущей даты, если в форме была введена дата-то с указанной.
     * При этом передаёт в модель обновлённый список просроченных задач "expired", экземпляр Month, и название.
     */
    @GetMapping("/")
    public String viewMonth(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        model.addAttribute("month", service.getAllTasksForMonth(date));
        model.addAttribute("monthName", Util.getMonthName(date));
        return "month";
    }

    /**
     * Представление "3 дня"
     * @param model Модель данных - класс, предоставляемый Spring
     * @param date Дата, передаваемая с клиента
     * @return Отдаёт представление "3 дня" за 3 дня с текущей даты, если в форме была введена дата-то с указанной.
     * При этом передаёт в модель обновлённый список просроченных задач "expired", и экземпляр ThreeDays.
     */
    @GetMapping("/3_days")
    public String viewThreeDays(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        model.addAttribute("threeDays", service.getAllTasksThreeDays(date));
        return "3_days";
    }

    /**
     * Представление "День"
     * @param model Модель данных - класс, предоставляемый Spring
     * @param date Дата, передаваемая с клиента
     * @return Отдаёт представление "День" за текущую даты, если в форме была введена дата-то за указанную.
     * При этом передаёт в модель обновлённый список просроченных задач "expired", и экземпляр Day.
     */
    @GetMapping("/day")
    public String viewDay(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        model.addAttribute("day", service.getAllDayTasks(date));
        return "day";
    }

    /**
     * Представление "Список"
     * @param model Модель данных - класс, предоставляемый Spring
     * @param date Дата, передаваемая с клиента
     * @return Отдаёт представление "Список", в котором отображаются задачи, подходящие по фильру поисковой
     * строки и даты. По умолчанию, дата - текущий день, а поисковая строка пуста - то есть все задачи.
     * При этом передаёт в модель обновлённый список просроченных задач "expired", и список задач для отображения.
     * В переменной urlAddress указывается адрес для последующей переадресации.
     */
    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        model.addAttribute("tasks", service.getAllTasksBySearchAndDate(search, date));
        model.addAttribute("urlAddress", "/list?search=" + search);
        return "list";
    }

    /**
     * Представление "Список" для просроченных задач
     * @param model Модель данных - класс, предоставляемый Spring
     * @return Отдаёт представление "Список", в котором отображаются только просроченные задачи.
     * При этом передаёт в модель обновлённые списки просроченных задач "expired","tasks".
     * В переменной urlAddress указывается адрес для последующей переадресации.
     */
    @GetMapping("/expired")
    public String viewListOfExpiredTasks(Model model) {
        List<Task> expiredTasks = service.getAllExpiredTasks();
        model.addAttribute(EXPIRED, expiredTasks);
        model.addAttribute("tasks", expiredTasks);
        model.addAttribute("urlAddress", "/expired");
        return "list";
    }

    /**
     * Представление "Задача" для просмотра имеющейся задачи
     * @param model Модель данных - класс, предоставляемый Spring
     * @param id Идентификатор задачи
     * @return Отдаёт представление "Задача" для отображения существующего экземпляра Task.
     * При этом передаёт в модель обновлённый список просроченных задач "expired"
     */
    @GetMapping("/task/{id}")
    public String viewTask(Model model, @PathVariable Long id) {
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        model.addAttribute("task", service.getTaskById(id));
        return "task";
    }

    /**
     * Представление "Задача"
     * @param model Модель данных - класс, предоставляемый Spring
     * @param date Дата, передаваемая с клиента
     * @return Отдаёт представление "Задача" для отображения нового экземпляра Task.
     * При этом передаёт в модель обновлённый список просроченных задач "expired"
     */
    @GetMapping("/task")
    public String viewNewTask(Model model, @RequestParam(value = "date", defaultValue = "nowDate") String date) {
        model.addAttribute("task", Util.prepareTask(date));
        model.addAttribute(EXPIRED, service.getAllExpiredTasks());
        return "task";
    }
}
