package ru.comodiary.diary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Title", "Description", "2024-05-22", "SET");
    }

    @Test
    void getReadableExpireDate() {
        String expired = "22.05.2024";
        String response = task.getReadableExpireDate();

        assertEquals(expired, response);
    }

    @Test
    void getWeekDay() {
        String expected = "СР";
        String response = task.getWeekDay();

        assertEquals(expected, response);
    }
}