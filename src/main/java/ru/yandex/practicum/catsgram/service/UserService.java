package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.dto.UserDtoResponse;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.UserMapper;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository rep) {
        this.userRepository = rep;
    }

    public Collection<UserDtoResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDtoResponse postUser(UserDtoRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else {
            Optional<User> alreadyExistUser = userRepository.findByEmail(request.getEmail());
            if (alreadyExistUser.isPresent()) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }

        User user = UserMapper.mapToUser(request);

        user = userRepository.save(user);

        return UserMapper.mapToUserDto(user);
    }

    public UserDtoResponse putUser(long userId, UserDtoRequest request) {
        User updatedUser = userRepository.findById(userId)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        updatedUser = userRepository.save(updatedUser);
        return UserMapper.mapToUserDto(updatedUser);
    }

    public UserDtoResponse getUserById(long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + userId));
    }

    private boolean isDuplicate(String value) {
        Optional<User> duplicate = userRepository.findDuplicate(value);

        return duplicate.isPresent();
    }
}
