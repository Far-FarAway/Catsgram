package ru.yandex.practicum.catsgram.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.marker.OnUpdate;
import ru.yandex.practicum.catsgram.model.Comment;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDtoRequest {
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    Long id;
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    Long authorId;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String description;
}
