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
    private long id;
    @PositiveOrZero
    private long postId;
    @NotBlank(groups = {onCreate.class})
    @NotNull(groups = {onCreate.class})
    private String description;
    @PositiveOrZero
    private long likesCount;
    @PastOrPresent
    private LocalDate date;
}
