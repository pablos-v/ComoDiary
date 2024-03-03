package ru.comodiary.diary.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.Month;
import ru.comodiary.diary.model.Task;
import ru.comodiary.diary.model.TaskStatus;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class TaskService {
    final int DAYS_VIEW = 4;

    private TaskRepository repository;

    @PostConstruct
    private void fakeData() {
        Task one = new Task("first", "ftest", LocalDate.of(2024, 3, 1), TaskStatus.NOT_COMPLETED);
        Task two = new Task("get bags", "stest", LocalDate.of(2024, 3, 2), TaskStatus.NOT_COMPLETED);
        Task three = new Task("buy food", "thtest", LocalDate.of(2024, 3, 31), TaskStatus.NOT_COMPLETED);
        Task four = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 4), TaskStatus.NOT_COMPLETED);
        Task four2 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four3 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four4 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four44 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four5 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four7 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 5), TaskStatus.NOT_COMPLETED);
        Task four6 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 6), TaskStatus.NOT_COMPLETED);
        addOrUpdateTask(one);
        addOrUpdateTask(two);
        addOrUpdateTask(three);
        addOrUpdateTask(four);
        addOrUpdateTask(four2);
        addOrUpdateTask(four3);
        addOrUpdateTask(four44);
        addOrUpdateTask(four4);
        addOrUpdateTask(four5);
        addOrUpdateTask(four6);
        addOrUpdateTask(four7);
    }


    public String getMonthName(String date) {
        int month = convertStringToLocalDate(date).getMonthValue();
        return String.format("Календарь задач на %s", monthToRus(month));
    }

    public Month getAllTasksForMonth(String date) {
        // берём первый день
        LocalDate startDate = convertStringToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        // берём последний день
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        // нужно для вывода номеров дат с корректного дня недели
        DayOfWeek startDayOfWeek = startDate.getDayOfWeek();
        int diffToMonday = startDayOfWeek.compareTo(DayOfWeek.MONDAY);

        List<Task> allTasksOfMonth = repository.findByExpireDateBetween(startDate, lastDate);

        return new Month(allTasksOfMonth, diffToMonday, lastDate.getDayOfMonth());
    }

    public List<Task> getAllTasksSomeDays(String date) {
        LocalDate startDate = convertStringToLocalDate(date);
        return repository.findByExpireDateBetween(startDate, startDate.plusDays(DAYS_VIEW));
    }

    public List<Task> getAllDayTasks(String date) {
        return repository.findByExpireDate(convertStringToLocalDate(date));
    }

    public List<Task> getAllTasksFromDate(String date) {
        return repository.findByExpireDateGreaterThanEqual(convertStringToLocalDate(date));
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id = %d is not found", id)));
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

    private LocalDate convertStringToLocalDate(String date) {
        if (Objects.equals(date, "nowDate")) return LocalDate.now();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String monthToRus(int month) {
        return switch (month) {
            case 1 -> "Январь";
            case 2 -> "Февраль";
            case 3 -> "Март";
            case 4 -> "Апрель";
            case 5 -> "Май";
            case 6 -> "Июнь";
            case 7 -> "Июль";
            case 8 -> "Август";
            case 9 -> "Сентябрь";
            case 10 -> "Октябрь";
            case 11 -> "Ноябрь";
            case 12 -> "Декабрь";
            default -> "месяц";
        };
    }
}
