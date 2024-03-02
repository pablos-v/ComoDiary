package ru.comodiary.diary.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Week {
    private List<Day> days;

    public Week() {
        this.days = new ArrayList<>();
    }
}
