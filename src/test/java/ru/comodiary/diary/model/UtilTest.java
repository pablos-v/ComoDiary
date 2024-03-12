package ru.comodiary.diary.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.TextStyle;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void stringToStatus() {
        TaskStatus expected = TaskStatus.NOT_COMPLETED;

        TaskStatus response = Util.stringToStatus("");

        assertEquals(expected, response);
    }

    @Test
    void getMonthName() {
        String expected = "Календарь задач на Май 2024 года";

        String response = Util.getMonthName("2024-05-22");

        assertEquals(expected, response);
    }

    @Test
    void convertStringToLocalDate() {
        LocalDate expected = LocalDate.of(2024,5,22);

        LocalDate response = Util.convertStringToLocalDate("2024-05-22");

        assertEquals(expected, response);
    }

    @Test
    void getMonthFromInt() {
        String expected = "Август";

        String response = Util.getMonthFromInt(8);

        assertEquals(expected, response);
    }

    @Test
    void prepareTask() {
        Task expected = new Task();
        expected.setStatus(TaskStatus.NOT_COMPLETED);
        expected.setExpireDate(LocalDate.of(2024,5,22));

        Task response = Util.prepareTask("2024-05-22");

        assertEquals(expected, response);
    }

    @Test
    void getDayOfWeek() {
        String expected = "СРЕДА";

        String response = Util.getDayOfWeek(TextStyle.FULL, LocalDate.of(2024,5,22));

        assertEquals(expected, response);
    }
}