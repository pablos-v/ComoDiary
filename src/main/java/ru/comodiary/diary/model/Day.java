package ru.comodiary.diary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
public class Day {
    private Integer number;
    private List<Task> tasks;
    private LocalDate date;
    private String dayName;
    private Boolean hasExpired;

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
        Locale ru = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        String weekDay = date.getDayOfWeek().getDisplayName(TextStyle.FULL, ru);

        return String.format("%s - %s", weekDay.toUpperCase(), dateFormat);
    }

    public Boolean hasExpired(){
        return tasks.stream()
                .anyMatch(task -> TaskStatus.EXPIRED.equals(task.getStatus()));
    }
}

