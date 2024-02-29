package ru.comodiary.diary.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    //    @Bean
//    public PasswordEncoder passwordEncoder() { // или можно создать свой вариант энкодера
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/ui/issues/**").hasAuthority("admin") // допуск по адресу только обладателям роли
//                        .requestMatchers("/ui/readers/**").hasAnyAuthority("user", "admin") // допуск по адресу только обладателям ролей
//                        .requestMatchers("/ui/readers/**").authenticated() // допуск по адресу только залогиненным, роли пофиг
//                        .requestMatchers("/ui/books/**").permitAll() // допуск по адресу для всех
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults()) // это форма ввода логина и пароля по умолчанию
//                .oauth2ResourceServer(configurer -> configurer // это через сервер авторизаций
//                        .jwt(Customizer.withDefaults())
//                )
                .build();
    }
}