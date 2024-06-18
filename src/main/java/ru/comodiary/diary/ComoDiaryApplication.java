package ru.comodiary.diary;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.comodiary.diary.security.CustomUserDetailsService;

/**
 * Точка входа в приложение
 */
@AllArgsConstructor
@SpringBootApplication
public class ComoDiaryApplication {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ComoDiaryApplication.class, args);
    }

    /**
     * Создание пользователя и пароля из аргументов командной строки при первом запуске
     * java -jar .\app.jar USER PASSWORD
     */
    @Bean
    public CommandLineRunner commandLineRunnerBean() {
        return args -> {
            if (args.length > 1) {
                CustomUserDetailsService userService = applicationContext.getBean(CustomUserDetailsService.class);
                userService.createUser(args[0], args[1]);
            }
        };
    }
}
