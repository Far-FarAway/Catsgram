package ru.yandex.practicum.catsgram.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dto.CommentDtoRequest;
import ru.yandex.practicum.catsgram.dto.CommentDtoResponse;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Comment;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentMapper {
    PostRepository postRepository;

    public Comment mapPojo(CommentDtoRequest dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new NotFoundException("Пост с id: " + dto.getPostId() + " не найден"));

        return Comment.builder()
                .id(dto.getId())
                .post(post)
                .description(dto.getDescription())
                .date(LocalDate.now())
                .build();

    }

    public CommentDtoResponse mapDto(Comment comment) {
        return CommentDtoResponse.builder()
                .post(comment.getPost())
                .description(comment.getDescription())
                .likesCount(comment.getLikesCount())
                .date(comment.getDate())
                .build();
    }
}
