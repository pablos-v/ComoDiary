package ru.comodiary.diary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Day {
    private Integer number;
    private List<Task> tasks;

    public Day(int number) {
        this.number = number;
        this.tasks = new ArrayList<>();
    }
}

