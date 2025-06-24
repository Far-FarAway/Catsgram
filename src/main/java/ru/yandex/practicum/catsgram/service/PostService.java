package ru.yandex.practicum.catsgram.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.dal.CommentRepository;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.CommentDtoRequest;
import ru.yandex.practicum.catsgram.dto.CommentDtoResponse;
import ru.yandex.practicum.catsgram.dto.PostDtoRequest;
import ru.yandex.practicum.catsgram.dto.PostDtoResponse;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.CommentMapper;
import ru.yandex.practicum.catsgram.mapper.PostMapper;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.Comment;

import java.util.*;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    UserService userService;
    UserRepository userRepository;
    CommentRepository commentRepository;
    PostMapper postMapper;
    CommentMapper commentMapper;

    public Collection<PostDtoResponse> findAll(Integer size, Integer from, String sort) {
        SortOrder order = SortOrder.from(sort);
        if (order == SortOrder.DESCENDING) {
            return postRepository.findAllByFiltersAndOrderDesc(size, from).stream()
                    .map(postMapper::mapDto)
                    .toList();
        } else if (order == SortOrder.ASCENDING) {
            return postRepository.findAllByFiltersAndOrderAsc(size, from).stream()
                    .map(postMapper::mapDto)
                    .toList();
        } else {
            throw new ConditionsNotMetException("Метод сортировки указан неверно: " + sort);
        }
    }

    public PostDtoResponse create(PostDtoRequest dto) {
        userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + dto.getAuthorId()));

        Post post = postMapper.mapPojo(dto);

        post.setPostDate(LocalDate.now());
        post.setComments(new HashMap<>());
        return postMapper.mapDto(postRepository.save(post));
    }

    public PostDtoResponse update(PostDtoRequest newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        Post oldPost = postRepository.findById(newPost.getId())
                .orElseThrow(() -> new NotFoundException("Пост с id " + newPost.getId() + " не найден"));
        if (newPost.getDescription() != null && !newPost.getDescription().isBlank()) {
            oldPost.setDescription(newPost.getDescription());
        }

        return postMapper.mapDto(oldPost);
    }

    public PostDtoResponse getPostById(long id) {
        return postMapper.mapDto(postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пост с id = " + id + " не найден")));
    }

    public Collection<Comment> getComments(LocalDate from, LocalDate until, long postId) {
        if (from == null && until != null) {
            return commentRepository.findAllByFilterUntil(until);
        } else if (from != null && until == null) {
            return commentRepository.findAllByFilterFrom(from);
        } else if (from != null) {
            return commentRepository.findAllByFilters(from, until);
        } else {
            return commentRepository.findAll();
        }
    }

    public CommentDtoResponse createComment(long postId, CommentDtoRequest dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Пост с id = " + postId + " не найден"));

        Comment comment = commentMapper.mapPojo(dto);

        comment.setPost(post);
        comment.setDate(LocalDate.now());

        return commentMapper.mapDto(commentRepository.save(comment));
    }
}
