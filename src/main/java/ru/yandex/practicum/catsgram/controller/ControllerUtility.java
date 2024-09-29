package ru.yandex.practicum.catsgram.controller;

import ru.yandex.practicum.catsgram.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ControllerUtility {

    public static boolean isDuplicate(Map<Long, User> map, String value) {
        Optional<User> duplicate = map.values().stream()
                .filter(person -> {
                    if(value.contains("@")) {
                       return person.getEmail().equals(value);
                    } else {
                        return person.getUsername().equals((value));
                    }
                }).findAny();

        return duplicate.isPresent();
    }

    public static long getNextId(Set<Long> list) {
        long currentMaxId = list.stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
