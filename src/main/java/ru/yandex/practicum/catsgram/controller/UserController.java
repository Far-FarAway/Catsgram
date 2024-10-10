package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.marker.onCreate;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService service) {
        userService = service;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        return userService.postUser(user);
    }

    @PutMapping
    @Validated(onCreate.class)
    public User putUser(@Valid @RequestBody User user) {
        return userService.putUser(user);
    }
}
