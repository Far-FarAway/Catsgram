package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.marker.onCreate;

import java.time.Instant;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else {
            if (ControllerUtility.isDuplicate(users, user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ConditionsNotMetException("Имя не должно быть пустым");
        } else {
            if(ControllerUtility.isDuplicate(users, user.getUsername())) {
                throw new DuplicatedDataException("Это имя уже занято");
            }
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ConditionsNotMetException("Пароль не должен быть пустой");
        }

        user.setId(ControllerUtility.getNextId(users.keySet()));
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    @Validated(onCreate.class)
    public User putUser(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            if (user.getEmail() != null && !(user.getEmail().isBlank())) {
                if (ControllerUtility.isDuplicate(users, user.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
                oldUser.setEmail(user.getEmail());
            }

            if (user.getUsername() != null && !(user.getUsername().isBlank())) {
                if (ControllerUtility.isDuplicate(users, user.getUsername())) {
                    throw new DuplicatedDataException("Это имя уже используется");
                }
                oldUser.setUsername(user.getUsername());
            }

            if (user.getPassword() != null) {
                oldUser.setPassword(user.getPassword());
            }

            return oldUser;
        }

        throw new NotFoundException("Пользователь с ID " + user.getId() + " не найден");
    }
}
