package ru.comodiary.diary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для шаблона представления day
 */
@Data
@NoArgsConstructor
public class Day {
    private Integer number;
    private List<Task> tasks;
    private LocalDate date;
    private String dayName;

    public Day(int number) {
        this.number = number;
        this.tasks = new ArrayList<>();
    }

    public Day(List<Task> tasks, LocalDate date) {
        this.tasks = tasks;
        this.date = date;
        this.dayName = dayName();
    }

    private String dayName(){
        String dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(this.date);
        String weekDay = Util.getDayOfWeek(TextStyle.FULL, this.date);

        return String.format("%s - %s", weekDay, dateFormat);
    }

    public Boolean hasExpired(){
        return tasks.stream()
                .anyMatch(task -> TaskStatus.EXPIRED.equals(task.getStatus()));
    }
}

