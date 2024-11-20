package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService service) {
        userService = service;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id).orElse(null);
    }

    @PostMapping
    @Validated(OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@Valid @RequestBody User user) {
        return userService.postUser(user);
    }

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        return userService.putUser(user);
    }
}
