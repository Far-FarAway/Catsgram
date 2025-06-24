package ru.yandex.practicum.catsgram.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.LocalDate;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDtoResponse {
    Post post;
    String description;
    long likesCount;
    LocalDate date;
}
