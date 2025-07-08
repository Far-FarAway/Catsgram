package ru.yandex.practicum.catsgram.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dal.CommentRepository;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.PostDtoRequest;
import ru.yandex.practicum.catsgram.dto.PostDtoResponse;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostMapper {
    UserRepository userRepository;
    CommentRepository commentRepository;

    public Post mapPojo(PostDtoRequest dto) {
        User user = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Пользователь  id: " + dto.getAuthorId() + " не найден"));

        return Post.builder()
                .id(dto.getId())
                .author(user)
                .description(dto.getDescription())
                .postDate(LocalDate.now())
                .build();
    }

    public PostDtoResponse mapDto(Post post) {
        return PostDtoResponse.builder()
                .authorId(post.getAuthor().getId())
                .description(post.getDescription())
                .postDate(post.getPostDate())
                .comments(commentRepository.findByPostId(post.getId()))
                .build();
    }
}
