package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.UserDto;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.UserMapper;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final UserRepository userRepository;

    public UserService(UserRepository rep) {
        this.userRepository = rep;
    }

    public Collection<UserDto> getUsers() {
        return userRepository.finadAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public User postUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else {
            if (isDuplicate(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ConditionsNotMetException("Имя не должно быть пустым");
        } else {
            if (isDuplicate(user.getUsername())) {
                throw new DuplicatedDataException("Это имя уже занято");
            }
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ConditionsNotMetException("Пароль не должен быть пустой");
        }

        user.setId(getNextId());
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
                if (isDuplicate(user.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
                oldUser.setEmail(user.getEmail());
            }

            if (user.getUsername() != null && !(user.getUsername().isBlank())) {
                if (isDuplicate(user.getUsername())) {
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

    private boolean isDuplicate(String value) {
        Optional<User> duplicate = users.values().stream()
                .filter(person -> {
                    if (value.contains("@")) {
                        return person.getEmail().equals(value);
                    } else {
                        return person.getUsername().equals((value));
                    }
                }).findAny();

        return duplicate.isPresent();
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public Optional<User> getUserById(long id) {
        return users.values().stream()
                .filter(person -> person.getId() == id)
                .findAny();
    }
}
