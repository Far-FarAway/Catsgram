package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.Comment;

import java.util.*;
import java.time.LocalDate;

@Service
public class PostService {
    private Comparator comparator = new Comparator() {
        @Override
        public int compare(Object post1, Object post2) {
            LocalDate date1 = ((Post) post1).getPostDate();
            LocalDate date2 = ((Post) post2).getPostDate();
            if (date1.isAfter(date2)) {
                return 3;
            } else if (date1.isBefore(date2)) {
                return -3;
            } else {
                return 0;
            }
        }
    };

    private final Map<Long, Post> posts = new HashMap<>();
    private final UserService userService;

    public PostService(UserService service) {
        userService = service;
    }

    public Collection<Post> findAll(Optional<Integer> size, Optional<Integer> from, String sort) {
        List<Post> postsList = new ArrayList<>(posts.values());
        postsList.sort(comparator);

        if (SortOrder.from(sort) == SortOrder.DESCENDING) {
            Collections.reverse(postsList);
        }

        if (size.isPresent() && from.isEmpty()) {
            return postsList.stream().limit(size.get()).toList();
        } else if (size.isEmpty() && from.isPresent()) {
            return postsList.stream().skip(from.get()).toList();
        } else if (size.isPresent())
            return postsList.stream().skip(from.get()).limit(size.get()).toList();
        else {
            return postsList;
        }
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        if (userService.getUserById(post.getAuthorId()).isEmpty()) {
            throw new ConditionsNotMetException("Пользователь с id " + post.getAuthorId() + " не найден");
        }

        post.setId(getNextPostId());
        post.setPostDate(LocalDate.now());
        post.setComments(new HashMap<>());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
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

    private long getNextCommentId(Map<Long, Comment> comments) {
        long currentMaxId = comments.keySet().stream()
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
        if (from.isPresent() && until.isPresent()) {
            return posts.get(postId).getComments().values().stream()
                    .filter(comment -> (comment.getDate().equals(from.get()) || comment.getDate().isAfter(from.get())) &&
                            comment.getDate().equals(until.get()) || comment.getDate().isBefore(until.get()))
                    .toList();
        } else {
            return posts.get(postId).getComments().values();
        }
    }

    public Comment createComment(long postId, Comment comment) {
        Map<Long, Comment> comments = posts.get(postId).getComments();

        comment.setId(getNextCommentId(comments));
        comment.setPostId(postId);
        comment.setDate(LocalDate.now());

        comments.put(comment.getId(), comment);
        return comment;
    }
}
