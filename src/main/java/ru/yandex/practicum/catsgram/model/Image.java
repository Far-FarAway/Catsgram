package ru.yandex.practicum.catsgram.model;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Image {
    Long id;
    Long postId;
    String originalFileName;
    String filePath;
}
