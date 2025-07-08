package ru.yandex.practicum.catsgram.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.marker.OnUpdate;

import java.time.LocalDate;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDtoRequest {
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    Long id;
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    Long postId;
    @NotBlank(groups = {OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    String description;
}
