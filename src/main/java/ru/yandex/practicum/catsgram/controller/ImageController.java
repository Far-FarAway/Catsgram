package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.RequiredArgsConstructor;

import ru.yandex.practicum.catsgram.model.Image;
import ru.yandex.practicum.catsgram.model.ImageData;
import ru.yandex.practicum.catsgram.service.ImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/posts/{postId}/images")
    public List<Image> getPostImages(@PathVariable long postId) {
        return imageService.getPostImages(postId);
    }

    @GetMapping(value = "/images/{imageId}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> downloadImage(@PathVariable long imageId) {
        ImageData imageData = imageService.getImageData(imageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment().filename(imageData.getName()).build()
        );

        return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Image> addPostImage(@PathVariable long postId,
                                    @RequestParam List<MultipartFile> images) {
        return imageService.saveImages(postId, images);
    }
}
