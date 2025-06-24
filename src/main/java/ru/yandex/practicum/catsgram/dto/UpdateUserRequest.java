package ru.yandex.practicum.catsgram.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequest {
    String username;
    @Email
    String email;
    String password;

    public boolean hasEmail() {
        return email == null;
    }

    public boolean hasPassword() {
        return password == null;
    }

    public boolean hasUsername() {
        return username == null;
    }
}
