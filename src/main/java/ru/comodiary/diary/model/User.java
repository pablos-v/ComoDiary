package ru.comodiary.diary.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Пользователь - сущность, хранящаяся в БД
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = "user";
    }

}
