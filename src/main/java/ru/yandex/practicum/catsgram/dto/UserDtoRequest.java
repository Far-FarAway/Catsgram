package ru.yandex.practicum.catsgram.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.marker.OnUpdate;

@Data
@Builder
public class UserDtoRequest {
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String username;
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;
    @NotBlank(groups = {OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    private String password;

    public boolean hasEmail() {
        return email != null;
    }

    public boolean hasUsername() {
        return username != null;
    }

    public boolean hasPassword() {
        return password != null;
    }
}