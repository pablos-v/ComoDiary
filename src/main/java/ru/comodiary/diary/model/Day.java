package ru.comodiary.diary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Day {
    private Integer number;
    private List<Task> tasks;
    private LocalDate date;

    public Day(int number) {
        this.number = number;
        this.tasks = new ArrayList<>();
    }
    public Day(List<Task> tasks, LocalDate date){
        this.tasks=tasks;
        this.date = date;
    }
}

