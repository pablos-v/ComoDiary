package ru.comodiary.diary.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO для шаблона представления 3_days
 */
@Data
public class ThreeDays {
    private final List<Day> days;
    private final LocalDate FirstDate;

}
