package ru.comodiary.diary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @NonNull
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "expire")
    private LocalDate expireDate;

    @Column(name = "status")
    private TaskStatus status;

}
