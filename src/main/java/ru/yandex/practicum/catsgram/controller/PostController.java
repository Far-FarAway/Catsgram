package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import ru.yandex.practicum.catsgram.dto.CommentDtoRequest;
import ru.yandex.practicum.catsgram.dto.CommentDtoResponse;
import ru.yandex.practicum.catsgram.dto.PostDtoRequest;
import ru.yandex.practicum.catsgram.dto.PostDtoResponse;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.marker.OnUpdate;
import ru.yandex.practicum.catsgram.model.Comment;
import ru.yandex.practicum.catsgram.marker.OnCreate;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.SortOrder;

import java.util.Collection;
import java.time.LocalDate;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Collection<PostDtoResponse> findAll(@RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "asc") String sort) {
        if (SortOrder.from(sort) == null) {
            throw new ParameterNotValidException("Некорректное значение параметра, должно быть ask или desc ", "sort");
        }

        if (size < 1) {
            throw new ParameterNotValidException("Параметр должен быть больше нуля", "size");
        }

        if (from < 0) {
            throw new ParameterNotValidException("Параметр не может быть меньше нуля", "from");
        }

        return postService.findAll(size, from, sort);
    }

    @GetMapping("/{id}")
    public PostDtoResponse getPost(@PathVariable long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/{postId}/comments")
    public Collection<Comment> getComments(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate until,
                                           @PathVariable long postId) {
        return postService.getComments(from, until, postId);
    }

    @PostMapping
    @Validated(OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public PostDtoResponse create(@Validated(OnCreate.class) @RequestBody PostDtoRequest dto) {
        return postService.create(dto);
    }

    @PostMapping("/{postId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse createComment(@PathVariable long postId,
                                            @Validated(OnCreate.class)@RequestBody CommentDtoRequest comment) {
        return postService.createComment(postId, comment);
    }

    @PutMapping
    public PostDtoResponse update(@Validated(OnUpdate.class) @RequestBody PostDtoRequest newDto) {
        return postService.update(newDto);
    }
}
