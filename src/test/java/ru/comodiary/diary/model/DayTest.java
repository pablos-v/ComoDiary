package ru.comodiary.diary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {
    Day day;

    @BeforeEach
    void setUp() {
        Task task = new Task("Title", "Description", "2024-05-22", "EXPIRED");
        day = new Day(List.of(task), LocalDate.of(2024,3,12));
    }

    @Test
    void hasExpired() {
        assertTrue(day.hasExpired());
    }

    @Test
    void getDayName() {
        String expected = "ВТОРНИК - 12.03.2024";
        String response = day.getDayName()  ;

        assertEquals(expected,response);
    }
}