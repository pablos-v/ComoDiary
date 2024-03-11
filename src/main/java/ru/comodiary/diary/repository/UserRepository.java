package ru.comodiary.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.comodiary.diary.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);


}