package ru.yandex.practicum.catsgram.model;

import java.time.Instant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import ru.yandex.practicum.catsgram.marker.onCreate;

@Data
@EqualsAndHashCode(of = {"email"})
public class User {
    @PositiveOrZero
    Long id;
    @NotNull(groups = {onCreate.class})
    @NotBlank(groups = {onCreate.class})
    String username;
    @Email
    String email;
    @NotNull(groups = {onCreate.class})
    @NotBlank(groups = {onCreate.class})
    String password;
    @FutureOrPresent
    Instant registrationDate;
}
