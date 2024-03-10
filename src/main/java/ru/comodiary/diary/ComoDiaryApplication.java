package ru.comodiary.diary;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.comodiary.diary.security.CustomUserDetailsService;

import java.util.Arrays;

@AllArgsConstructor
@SpringBootApplication
public class ComoDiaryApplication {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ComoDiaryApplication.class, args);
    }

    // создание пользователя и пароля из аргументов при первом запуске
    // java -jar .\app.jar USER PASSWORD
    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            if (args.length > 1) {
                CustomUserDetailsService userService = applicationContext.getBean(CustomUserDetailsService.class);
                userService.createUser(args[0], args[1]);
            }
        };
    }
}
