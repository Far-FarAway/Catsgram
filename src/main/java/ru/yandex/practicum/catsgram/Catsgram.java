package ru.yandex.practicum.catsgram;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Catsgram {
    public static void main(String[] args) {
        SpringApplication.run(Catsgram.class, args);

        /*final Gson gson = new Gson();
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Введите JSON => ");
        final String input = scanner.nextLine();
        try {
            gson.fromJson(input, Map.class);
            System.out.println("Был введён корректный JSON");
        } catch (JsonSyntaxException exception) {
            System.out.println("Был введён некорректный JSON");
        }*/
        System.out.println(new User((long)2, "dsds", "ffff", "3214", Instant.now())
                .getUsername());
    }
}
