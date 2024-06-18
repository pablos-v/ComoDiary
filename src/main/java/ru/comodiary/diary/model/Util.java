package ru.comodiary.diary.model;

import ru.comodiary.diary.controller.ViewController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Вспомогательный класс со статическими методами
 */
public final class Util {

    /**
     * Конвертирует Строковое представление статуса в enum объект TaskStatus
     * @param s статус в формате строки
     * @return статус в формате TaskStatus
     */
    public static TaskStatus stringToStatus(String s) {
        return switch (s) {
            case "DONE" -> TaskStatus.COMPLETED;
            case "EXPIRED" -> TaskStatus.EXPIRED;
            default -> TaskStatus.NOT_COMPLETED;
        };
    }

    /**
     * Конвертирует Строковое представление даты в удобочитаемый заголовок
     * @param date строковое представление даты
     * @return готовый заголовок
     */
    public static String getMonthName(String date) {
        LocalDate localDate = convertStringToLocalDate(date);
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("Календарь задач на %s %d года", getMonthFromInt(month), year);
    }

    /**
     * Конвертирует Строковое представление даты в тип LocalDate
     * @param date строковое представление даты
     * @return дата типа LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date) {
        if (Objects.equals(date, "nowDate")) return LocalDate.now();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Конвертирует числовое представление месяца в строковое представление
     * @param month числовое представление месяца
     * @return строковое представление месяца
     */
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

    /**
     * Готовит объект задачи на основе указанной даты.
     * Используется при создании новой задачи в представлении "Задача" через ViewController {@link ViewController}
     * @param date строковое представление даты
     * @return объект задачи
     */
    public static Task prepareTask(String date) {
        Task task = new Task();
        task.setExpireDate(convertStringToLocalDate(date));
        task.setStatus(TaskStatus.NOT_COMPLETED);
        return task;
    }

    /**
     * Конвертирует дату и заданный TextStyle в строку с названием для недели
     * @param textStyle объект enum TextStyle
     * @param date строковое представление даты
     * @return строковое представление дня недели
     */
    public static String getDayOfWeek(TextStyle textStyle, LocalDate date) {
        Locale ru = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        return date.getDayOfWeek().getDisplayName(textStyle, ru).toUpperCase();
    }
}
