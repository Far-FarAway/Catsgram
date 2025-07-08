package ru.yandex.practicum.catsgram.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.dto.UserDtoResponse;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static User mapToUser(UserDtoRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRegistrationDate(Instant.now());

        return user;
    }

    public static UserDtoResponse mapToUserDto(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(Instant.now());
        return dto;
    }

    public static User updateUserFields(User user, UserDtoRequest request) {
        if (request.hasEmail()) {
            user.setEmail(request.getEmail());
        }
        if (request.hasPassword()) {
            user.setPassword(request.getPassword());
        }
        if (request.hasUsername()) {
            user.setUsername(request.getUsername());
        }
        return user;
    }
}
