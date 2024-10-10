package ru.yandex.practicum.catsgram.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.marker.onCreate;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @GetMapping
    public Collection<Post> findAll() {
        return postService.findAll();
    }

    @PostMapping
    public Post create(@Valid @RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    @Validated(onCreate.class)
    public Post update(@Valid @RequestBody Post newPost) {
        return postService.update(newPost);
    }
}
