package ru.comodiary.diary.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Вспомогательный класс со статическими методами
 */
public final class Util {
    private Util() {
    }

    public static TaskStatus stringToStatus(String s) {
        return switch (s) {
            case "DONE" -> TaskStatus.COMPLETED;
            case "EXPIRED" -> TaskStatus.EXPIRED;
            default -> TaskStatus.NOT_COMPLETED;
        };
    }

    public static String getMonthName(String date) {
        LocalDate localDate = convertStringToLocalDate(date);
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("Календарь задач на %s %d года", getMonthFromInt(month), year);
    }

    public static LocalDate convertStringToLocalDate(String date) {
        if (Objects.equals(date, "nowDate")) return LocalDate.now();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getMonthFromInt(int month) {
        return switch (month) {
            case 1 -> "Январь";
            case 2 -> "Февраль";
            case 3 -> "Март";
            case 4 -> "Апрель";
            case 5 -> "Май";
            case 6 -> "Июнь";
            case 7 -> "Июль";
            case 8 -> "Август";
            case 9 -> "Сентябрь";
            case 10 -> "Октябрь";
            case 11 -> "Ноябрь";
            default -> "Декабрь";
        };
    }

    public static Task prepareTask(String date) {
        Task task = new Task();
        task.setExpireDate(convertStringToLocalDate(date));
        task.setStatus(TaskStatus.NOT_COMPLETED);
        return task;
    }

    public static String getDayOfWeek(TextStyle textStyle, LocalDate date) {
        Locale ru = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        return date.getDayOfWeek().getDisplayName(textStyle, ru).toUpperCase();
    }
}
