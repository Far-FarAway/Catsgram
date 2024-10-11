package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;

import ru.yandex.practicum.catsgram.marker.onCreate;

import java.time.LocalDate;

@Data
public class Comment {
    @PositiveOrZero
    long id;
    @PositiveOrZero
    long postId;
    @NotBlank(groups = {onCreate.class})
    @NotNull(groups = {onCreate.class})
    String description;
    @PositiveOrZero
    long likesCount;
    @PastOrPresent
    LocalDate date;
}
