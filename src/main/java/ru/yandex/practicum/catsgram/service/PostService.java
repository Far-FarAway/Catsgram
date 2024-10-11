package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.Comment;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class PostService {
    private final Map<Long, Post> posts = new HashMap<>();
    UserService userService;

    public PostService(UserService service) {
        userService = service;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post create(Post post) {
        if(post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        if (userService.getUserById(post.getAuthorId()).isEmpty()) {
            throw new ConditionsNotMetException("Пользователь с id " + post.getAuthorId() + " не найден");
        }

        post.setId(getNextPostId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update( Post newPost) {
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

    private long getNextPostId() {
        long currentMaxId = posts.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private long getNextCommentId(Post post) {
        long currentMaxId = post.getComments().keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public Optional<Post> getPostById(long id) {
        return posts.values().stream()
                .filter(post -> post.getId() == id)
                .findAny();
    }

    public Collection<Comment> getComments(Optional<LocalDate> from, Optional<LocalDate> until, long postId) {
        if(from.isPresent() && until.isPresent()) {
            return posts.get(postId).getComments().values().stream()
                    .filter(comment -> (comment.getDate().equals(from.get()) || comment.getDate().isAfter(from.get())) &&
                            comment.getDate().equals(until.get()) || comment.getDate().isBefore(until.get()))
                    .toList();
        } else {
            return posts.get(postId).getComments().values();
        }
    }
}
