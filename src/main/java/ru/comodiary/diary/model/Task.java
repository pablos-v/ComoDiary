package ru.comodiary.diary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;

/**
 * Основная сущность, хранящаяся в БД
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "expire")
    private LocalDate expireDate;

    @Column(name = "status")
    private TaskStatus status;

    public Task(String title, String description, LocalDate expireDate) {
        this.title = title;
        this.description = description;
        this.expireDate = expireDate;
        this.status = TaskStatus.NOT_COMPLETED;
    }

    public Task(String title, String description, String expireDate, String status) {
        this.title = title;
        this.description = description;
        this.expireDate = LocalDate.parse(expireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.status = Util.stringToStatus(status);
    }

    /**
     * Метод используется шаблонами Thymeleaf для отображения даты в читабельном формате.
     * @return Отформатированная дата
     */
    public String getReadableExpireDate() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(this.expireDate);
    }

    /**
     * Метод используется шаблонами Thymeleaf для отображения дня недели в заданном формате.
     * @return Сокращённое написание дня недели
     */
    public String getWeekDay() {
        return Util.getDayOfWeek(TextStyle.SHORT, this.expireDate);
    }
}
