package ru.yandex.practicum.catsgram.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.catsgram.model.Post;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDtoResponse {
    Post post;
    String originalFileName;
    String filePath;
}
