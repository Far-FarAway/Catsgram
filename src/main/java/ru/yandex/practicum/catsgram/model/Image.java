package ru.yandex.practicum.catsgram.model;

import lombok.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import ru.yandex.practicum.catsgram.marker.OnCreate;

@Data
@EqualsAndHashCode(of = {"id"})
public class Image {
    @PositiveOrZero
    private Long id;
    @PositiveOrZero
    private Long postId;
    @NotBlank(groups = {OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    private String originalFileName;
    @NotBlank(groups = {OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    private String filePath;
}
