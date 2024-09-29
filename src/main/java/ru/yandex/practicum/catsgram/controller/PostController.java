package ru.yandex.practicum.catsgram.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.marker.onCreate;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final Map<Long, Post> posts = new HashMap<>();

    @GetMapping
    public Collection<Post> findAll() {
        return posts.values();
    }

    @PostMapping
    public Post create(@Valid @RequestBody Post post) {
        if(post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        post.setId(ControllerUtility.getNextId(posts.keySet()));
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    @PutMapping
    @Validated(onCreate.class)
    public Post update(@Valid @RequestBody Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() != null && !newPost.getDescription().isBlank()) {
                oldPost.setDescription(newPost.getDescription());
            }

            return oldPost;
        }

        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }
}
