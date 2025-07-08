package ru.yandex.practicum.catsgram.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.catsgram.dal.CommentRepository;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dto.PostDtoRequest;
import ru.yandex.practicum.catsgram.dto.PostDtoResponse;
import ru.yandex.practicum.catsgram.mapper.PostMapper;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    PostMapper postMapper;
    @InjectMocks
    @Autowired
    PostService postService;

    @Test
    void testUpdatePost() {
        PostDtoRequest postRequest = PostDtoRequest.builder()
                .id(3L)
                .authorId(3423423L)
                .description("test")
                .build();

        Mockito.when(postRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Post.builder().id(123132123L).author(new User()).build()));

        Mockito.when(postRepository.save(Mockito.any(Post.class)))
                .thenAnswer(invoc -> invoc.getArgument(0));

        Mockito.when(postMapper.mapDto(Mockito.any(Post.class)))
                .thenAnswer(invoc -> {
                    Post post = invoc.getArgument(0);
                    return PostDtoResponse.builder()
                            .description(post.getDescription())
                            .build();
                });

        PostDtoResponse post = postService.update(postRequest);

        assertThat(post, hasProperty("description"));
        assertThat(post.getDescription(), allOf(notNullValue(), equalTo(postRequest.getDescription())));
    }
}