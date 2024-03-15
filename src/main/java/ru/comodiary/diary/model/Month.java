package ru.comodiary.diary.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для шаблона представления month
 */
@Data
public class Month {
    private final List<Week> weeks;
    private LocalDate firstDate;
    private LocalDate nowDate;

    public Month(List<Task> allTasksOfMonth, int diffToMonday, LocalDate lastDate) {
        this.weeks = prepareWeeks(allTasksOfMonth, diffToMonday, lastDate);
        this.firstDate = lastDate.withDayOfMonth(1);
        this.nowDate = LocalDate.now();
    }

    private List<Week> prepareWeeks(List<Task> allTasksOfMonth, int diffToMonday, LocalDate lastDate) {
        List<Week> weeks = new ArrayList<>();
        byte last = (byte) lastDate.getDayOfMonth();
        byte counter = 1;
        // для создания ровной таблицы на месяц нужны 5 недель
        for (int i = 0; i < 5; i++) {
            Week week = new Week();
            for (int d = 0; d < 7; d++) {
                Day day;
                // таблица начинается с понедельника - чтобы все ячейки были заполнены, нужно проверить с какого
                // дня стартует месяц и вставить в таблицу пустые дни предыдущего и следующего месяцев
                if (diffToMonday > 0) {
                    day = new Day();
                    diffToMonday--;
                    // здесь считаются дни месяца и добавляются задачи в Day
                } else if (counter <= last) {
                    day = new Day(counter);
                    day.setDate(lastDate.withDayOfMonth(counter++));
                    if (!allTasksOfMonth.isEmpty()) {
                        for (Task task : allTasksOfMonth) {
                            if (day.getDate().equals(task.getExpireDate())) {
                                day.getTasks().add(task);
                            }
                        }
                    }
                } else day = new Day();
                week.getDays().add(day);
            }
            weeks.add(week);
        }
        return weeks;
    }
}
