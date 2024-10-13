package ru.yandex.practicum.catsgram.model;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import ru.yandex.practicum.catsgram.marker.onCreate;

import java.time.LocalDate;
import java.util.Map;

@Data
@EqualsAndHashCode(of = {"id"})
public class Post {
    @PositiveOrZero
    private Long id;
    @PositiveOrZero
    private Long authorId;
    @NotNull(groups = {onCreate.class})
    @NotBlank(groups = {onCreate.class})
    private String description;
    @FutureOrPresent
    LocalDate postDate;
    private Map<Long, Comment> comments;
}
