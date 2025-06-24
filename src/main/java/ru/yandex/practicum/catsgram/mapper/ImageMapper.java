package ru.yandex.practicum.catsgram.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dto.ImageDtoResponse;
import ru.yandex.practicum.catsgram.model.Image;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageMapper {
    public ImageDtoResponse mapDto(Image image) {
        return ImageDtoResponse.builder()
                .post(image.getPost())
                .originalFileName(image.getOriginalFileName())
                .filePath(image.getFilePath())
                .build();
    }
}
