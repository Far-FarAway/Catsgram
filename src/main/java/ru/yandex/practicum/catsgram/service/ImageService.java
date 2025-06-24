package ru.yandex.practicum.catsgram.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import ru.yandex.practicum.catsgram.dal.ImageRepository;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dto.ImageDtoResponse;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.ImageFileException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.ImageMapper;
import ru.yandex.practicum.catsgram.model.Image;
import ru.yandex.practicum.catsgram.model.ImageData;
import ru.yandex.practicum.catsgram.model.Post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    PostRepository postRepository;
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    String imageDirectory = "/Users/vadimkatkov/Desktop/IdeaProjects/Catsgram/images";

    public List<ImageDtoResponse> getPostImages(long postId) {
        return imageRepository.findByPostId(postId).stream()
                .map(imageMapper::mapDto)
                .toList();
    }

    public ImageData getImageData(long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Изображения с id " + imageId + "не найдено"));

        byte[] data = loadFile(image);

        return new ImageData(data, image.getOriginalFileName());
    }

    private byte[] loadFile(Image image) {
        Path path = Paths.get(image.getFilePath());

        if (Files.exists(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException ex) {
                throw new ImageFileException("Ошибка чтения файла\nid: " + image.getId() +
                        "name: " + image.getOriginalFileName(), ex);
            }
        } else {
            throw new ImageFileException("Файл не найден. Id: " + image.getId()
                    + ", name: " + image.getOriginalFileName());
        }
    }

    public List<ImageDtoResponse> saveImages(long postId, List<MultipartFile> files) {
        return files.stream()
                .map(file -> saveImage(postId, file))
                .map(imageMapper::mapDto)
                .toList(); //аналогично
    }

    private Image saveImage(long postId, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ConditionsNotMetException("Пост с id " + postId + "не найден"));

        Path filePath = saveFile(file, post);

        Image image = new Image();
        image.setFilePath(filePath.toString());
        image.setPost(post);
        image.setOriginalFileName(file.getOriginalFilename());

        return imageRepository.save(image);
    }

    private Path saveFile(MultipartFile file, Post post) {
        try {
            String uniqueFileName = String.format("%d.%s", Instant.now().toEpochMilli(),
                    StringUtils.getFilenameExtension(file.getOriginalFilename()));
            Path uploadPath = Paths.get(imageDirectory, String.valueOf(post.getAuthor().getId()), post.getId().toString());
            Path filePath = uploadPath.resolve(uniqueFileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            file.transferTo(filePath);
            return filePath;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
