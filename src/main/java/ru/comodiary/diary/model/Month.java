package ru.comodiary.diary.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Month {
    private String name;
    private final List<Week> weeks;
    private LocalDate firstDate;
    private LocalDate nowDate;

    public Month(List<Task> allTasksOfMonth, int diffToMonday, LocalDate lastDate) {
        this.weeks = new ArrayList<>();
        this.firstDate = lastDate.withDayOfMonth(1);
        this.nowDate = LocalDate.now();
        byte counter = 1;
        byte last = (byte) lastDate.getDayOfMonth();

        for (int i = 0; i < 5; i++) {
            Week week = new Week();

            for (int d = 0; d < 7; d++) {
                Day day;
                // вью будет с понедельника, чтобы все ячейки были заполнены,
                // проверяем с какого дня стартует месяц и добиваем пустые дни
                if (diffToMonday > 0) {
                    day = new Day();
                    diffToMonday--;
                } else if (counter <= last) {
                    day = new Day(counter);
                    day.setDate(lastDate.withDayOfMonth(counter++));
                    // всунуть в день его таски
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

            this.weeks.add(week);
        }
    }
}
