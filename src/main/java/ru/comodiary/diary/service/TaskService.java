package ru.comodiary.diary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.comodiary.diary.model.*;
import ru.comodiary.diary.repository.TaskRepository;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;

    //        @PostConstruct
    private void fdata() {
        Task four6 = new Task("ther mushrooms", "fourtest", LocalDate.of(2024, 4, 5), TaskStatus.EXPIRED);
        addOrUpdateTask(four6);
    }

    private void fakeData() {
        Task one = new Task("first", "ftest", LocalDate.of(2024, 3, 1), TaskStatus.NOT_COMPLETED);
        Task two = new Task("get bags", "stest", LocalDate.of(2024, 3, 2), TaskStatus.NOT_COMPLETED);
        Task three = new Task("buy food", "thtest", LocalDate.of(2024, 3, 31), TaskStatus.NOT_COMPLETED);
        Task four = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 4), TaskStatus.NOT_COMPLETED);
        Task four2 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four3 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four4 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four44 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 3), TaskStatus.NOT_COMPLETED);
        Task four5 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 5), TaskStatus.NOT_COMPLETED);
        Task four7 = new Task("go to the forest to gather mushrooms", "fourtest", LocalDate.of(2024, 4, 5), TaskStatus.COMPLETED);
        Task four6 = new Task("ther mushrooms", "fourtest", LocalDate.of(2024, 4, 5), TaskStatus.EXPIRED);
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
        LocalDate localDate = convertStringToLocalDate(date);
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("Календарь задач на %s %d года", monthToRus(month), year);
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

        return new Month(allTasksOfMonth, diffToMonday, lastDate);
    }

    public ThreeDays getAllTasksThreeDays(String date) {
        LocalDate startDate = convertStringToLocalDate(date);
        return new ThreeDays(repository.findByExpireDateBetween(startDate, startDate.plusDays(3)), startDate);
    }

    public Day getAllDayTasks(String date) {
        LocalDate localDate = convertStringToLocalDate(date);
        return new Day(repository.findByExpireDate(localDate), localDate);
    }

    public Day getAllDayTasks(LocalDate date) {
        return new Day(repository.findByExpireDate(date), date);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(()
                -> new RuntimeException(String.format("Task with id = %d is not found", id)));
    }

    public Task addOrUpdateTask(Task task) {
        return repository.save(task);
    }

    public Task deleteTaskById(Long id) {
        // не нужно проверять на экзист, это сделает getTaskById(id)
        Task result = getTaskById(id);
        repository.deleteById(id);
        return result;
    }

    public LocalDate convertStringToLocalDate(String date) {
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

    public String getDayName(String date) {
        LocalDate localDate = convertStringToLocalDate(date);
        String dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(localDate);
        Locale ru = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        String weekDay = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, ru);

        return String.format("%s,  %s", dateFormat, weekDay);
    }

    public List<Task> getAllTasksBySearch(String search) {
        return repository.findByTitleContainingOrDescriptionContaining(search, search);
    }

    public List<Task> updateAndGetAllExpiredTasks() {
        // найти все невып таски младше даты
        List<Task> tasks = repository.findByExpireDateLessThanAndStatusEquals(LocalDate.now(), TaskStatus.NOT_COMPLETED);
        // всем менять статус
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.EXPIRED);
            repository.saveAll(tasks);
        }
        // найти среди просроченных таски старше или равные даты
        tasks = repository.findByExpireDateGreaterThanEqualAndStatusEquals(LocalDate.now(), TaskStatus.EXPIRED);
        // менять статусы
        if (!tasks.isEmpty()) {
            for (Task task : tasks) task.setStatus(TaskStatus.NOT_COMPLETED);
            repository.saveAll(tasks);
        }
        return repository.findByStatus(TaskStatus.EXPIRED);
    }

    public Task changeStatus(Long id) {
        Task task = getTaskById(id);
        task.setStatus(task.getStatus() == TaskStatus.COMPLETED ? TaskStatus.NOT_COMPLETED : TaskStatus.COMPLETED);
        repository.save(task);
        return task;
    }
}
