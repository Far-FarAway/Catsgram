package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.controller.ControllerUtility;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User postUser(User user) {
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

    public User putUser(User user) {
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
