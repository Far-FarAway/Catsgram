package ru.yandex.practicum.catsgram.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.Comment;
import ru.yandex.practicum.catsgram.marker.onCreate;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/posts")
public class PostController {

    PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam Optional<Integer> size,
                                    @RequestParam Optional<Integer> from,
                                    @RequestParam(defaultValue = "asc")String sort) {
        return postService.findAll(size, from, sort);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable long id) {
        return postService.getPostById(id).orElse(null);
    }

    @GetMapping("/{postId}/comments")
    public Collection<Comment> getComments(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Optional<LocalDate> from,
                                           @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Optional<LocalDate> until,
                                           @PathVariable long postId) {
        return postService.getComments(from, until, postId);
    }

    @PostMapping
    @Validated(onCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@Valid @RequestBody Post post) {
        return postService.create(post);
    }

    @PostMapping("/{postId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@PathVariable long postId, @RequestBody Comment comment) {
        return postService.createComment(postId, comment);
    }

    @PutMapping
    public Post update(@Valid @RequestBody Post newPost) {
        return postService.update(newPost);
    }
}
