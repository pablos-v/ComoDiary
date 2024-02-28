package ru.comodiary.diary.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.repository.TaskRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id= {%d} is not found", id)));
    }

    // TODO тестить как с ним работает PUT
    public Task addOrUpdateTask(Task task) {
        return repository.save(task);
    }

    public Task deleteTaskById(long id) {
        // не нужно проверять на экзист, это сделает getTaskById(id)
        Task result = getTaskById(id);
        repository.deleteById(id);
        return result;
    }
}
