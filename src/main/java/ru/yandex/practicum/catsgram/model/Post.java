package ru.yandex.practicum.catsgram.model;

import java.time.Instant;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import ru.yandex.practicum.catsgram.marker.onCreate;

import java.util.Map;

@Data
@EqualsAndHashCode(of = {"id"})
public class Post {
    @PositiveOrZero
    Long id;
    @PositiveOrZero
    Long authorId;
    @NotNull(groups = {onCreate.class})
    @NotBlank(groups = {onCreate.class})
    String description;
    @FutureOrPresent
    Instant postDate;
    Map<Long, Comment> comments;
}
