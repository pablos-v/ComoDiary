package ru.comodiary.diary.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Month {
    private String name;
    private final List<Week> weeks;

    public Month(List<Task> allTasksOfMonth, int diffToMonday, int lastDate) {
        this.weeks = new ArrayList<>();
        int counter = 1;

        for (int i = 0; i < 5; i++) {
            Week week = new Week();

            for (int d = 0; d < 7; d++) {
                Day day;
                // вью будет с понедельника, чтобы все ячейки были заполнены,
                // проверяем с какого дня стартует месяц и добиваем пустые дни
                if (diffToMonday > 0) {
                    day = new Day();
                    diffToMonday--;
                } else if (counter <= lastDate) {
                    day = new Day(counter++);

                    // всунуть в день его таски
                    if (!allTasksOfMonth.isEmpty()) {
                        for (Task task : allTasksOfMonth) {
                            if (day.getNumber() == task.getExpireDate().getDayOfMonth()) {
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
