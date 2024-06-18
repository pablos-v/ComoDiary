package ru.comodiary.diary.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.comodiary.diary.model.User;
import ru.comodiary.diary.repository.UserRepository;

import java.util.List;

/**
 * Составляющая конфигурации безопасности
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Создаёт пользователя, если пользователя ещё не существует.
     * Пароль шифруется и записывается в БД в шифрованном виде.
     * @param user Имя пользователя
     * @param rawPassword пароль пользователя
     */
    @Transactional
    public void createUser(String user, String rawPassword) {
        if (userRepository.findAll().isEmpty()) {
            String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
            userRepository.save(new User(user, encodedPassword));
        }
    }

    /**
     * Метод авторизации пользователя, определённый в UserDetailsService
     * @param username Имя пользователя
     * @return объект UserDetails, содержащий информацию о пользователе
     * @throws UsernameNotFoundException если такого пользователя нет
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), List.of(
                new SimpleGrantedAuthority(user.getRole())
        ));
    }
}