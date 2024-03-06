package ru.comodiary.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.service.TaskService;

import java.util.Objects;

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

    // фактически это PUT, но HTML формы поддерживают только 2 метода GET и POST... ссыль в литературе есть
    @PostMapping("/task/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("expireDate") String expireDate,
                             @RequestParam(name = "status", defaultValue = "no") String status,
                             Model model) {
        Task task = service.getTaskById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setExpireDate(service.convertStringToLocalDate(expireDate));
        if (!status.equals("no")) task.setStringStatus(status);

        service.addOrUpdateTask(task);
        model.addAttribute("day", service.getAllDayTasks(task.getExpireDate().toString()));
        return "day";
    }

    // удаляет задачу и редиректит обратно в день
    @PostMapping("/delete/{id}")
    public ModelAndView deleteById(@PathVariable Long id, Model model) {
        Task task = service.deleteTaskById(id);
        model.addAttribute("day", service.getAllDayTasks(task.getExpireDate().toString()));
        return new ModelAndView("redirect:" + "/day?date=" + task.getExpireDate());
    }
// унифицировать и получать ИД в @RequestParam ???
    @PostMapping("/change-status/go-to-day/{id}")
    public ModelAndView changeStatusByIdAndGoToDayView(@PathVariable Long id, Model model) {
        Task task = service.changeStatus(id);
        model.addAttribute("day", service.getAllDayTasks(task.getExpireDate().toString()));
        return new ModelAndView("redirect:" + "/day?date=" + task.getExpireDate());
    }

    @PostMapping("/change-status/go-to-list/{id}")
    public ModelAndView changeStatusByIdAndGoToListView(@PathVariable Long id, Model model) {
        Task task = service.changeStatus(id);
        model.addAttribute("day", service.getAllDayTasks(task.getExpireDate().toString()));
        return new ModelAndView("redirect:" + "/list?search= ТУТ ПАРАМЕТР из @RequestParam" );
    }

}
