package ru.yandex.practicum.catsgram.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;

import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.dto.UserDtoResponse;
import ru.yandex.practicum.catsgram.marker.OnUpdate;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Collection<UserDtoResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDtoResponse getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoResponse postUser(@Validated(OnCreate.class) @RequestBody UserDtoRequest userRequest) {
        return userService.postUser(userRequest);
    }

    @PutMapping("/{userId}")
    public UserDtoResponse putUser(@PathVariable long userId,
                                   @Validated(OnUpdate.class) @RequestBody UserDtoRequest request) {
        return userService.putUser(userId, request);
    }
}
