package ru.comodiary.diary.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class ThreeDays {
    private final List<Day> days;
    private final LocalDate FirstDate;

}
