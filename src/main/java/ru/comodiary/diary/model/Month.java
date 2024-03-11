package ru.comodiary.diary.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public String getName() {
        return this.name;
    }

    public List<Week> getWeeks() {
        return this.weeks;
    }

    public LocalDate getFirstDate() {
        return this.firstDate;
    }

    public LocalDate getNowDate() {
        return this.nowDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public void setNowDate(LocalDate nowDate) {
        this.nowDate = nowDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Month)) return false;
        final Month other = (Month) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$weeks = this.getWeeks();
        final Object other$weeks = other.getWeeks();
        if (this$weeks == null ? other$weeks != null : !this$weeks.equals(other$weeks)) return false;
        final Object this$firstDate = this.getFirstDate();
        final Object other$firstDate = other.getFirstDate();
        if (this$firstDate == null ? other$firstDate != null : !this$firstDate.equals(other$firstDate)) return false;
        final Object this$nowDate = this.getNowDate();
        final Object other$nowDate = other.getNowDate();
        if (this$nowDate == null ? other$nowDate != null : !this$nowDate.equals(other$nowDate)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Month;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $weeks = this.getWeeks();
        result = result * PRIME + ($weeks == null ? 43 : $weeks.hashCode());
        final Object $firstDate = this.getFirstDate();
        result = result * PRIME + ($firstDate == null ? 43 : $firstDate.hashCode());
        final Object $nowDate = this.getNowDate();
        result = result * PRIME + ($nowDate == null ? 43 : $nowDate.hashCode());
        return result;
    }

    public String toString() {
        return "Month(name=" + this.getName() + ", weeks=" + this.getWeeks() + ", firstDate=" + this.getFirstDate() + ", nowDate=" + this.getNowDate() + ")";
    }
}
