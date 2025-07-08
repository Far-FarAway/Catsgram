package ru.yandex.practicum.catsgram.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.catsgram.model.Comment;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDtoResponse {
    Long authorId;
    String description;
    LocalDate postDate;
    List<Comment> comments;
}
