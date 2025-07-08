package ru.yandex.practicum.catsgram.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.CommentDtoRequest;
import ru.yandex.practicum.catsgram.dto.CommentDtoResponse;
import ru.yandex.practicum.catsgram.dto.PostDtoRequest;
import ru.yandex.practicum.catsgram.dto.PostDtoResponse;
import ru.yandex.practicum.catsgram.mapper.PostMapper;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@TestPropertySource(properties = {"spring.datasource.url=jdbc:postgresql://localhost:5433/testDB",
        "spring.datasource.username=test",
        "spring.datasource.password=test"})
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
class PostServiceIntegrationTest {
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;
    User testUser;
    PostDtoRequest request1;
    PostDtoRequest request2;
    CommentDtoRequest commRequest1;
    CommentDtoRequest commRequest2;

    @BeforeEach
    void before() {
        request1 = PostDtoRequest.builder()
                .description("test")
                .build();

        request2 = PostDtoRequest.builder()
                .description("test2")
                .build();

        commRequest1 = CommentDtoRequest.builder()
                .description("comment test")
                .build();

        commRequest2 = CommentDtoRequest.builder()
                .description("comment test2")
                .build();

        testUser = User.builder()
                .username("SuperTestUser")
                .email("testuser@gmail.com")
                .password("testiculs")
                .registrationDate(Instant.now())
                .build();
    }

    @Test
    void testPostAndGetPost() {
        User user = userRepository.save(testUser);

        request1.setAuthorId(user.getId());
        request2.setAuthorId(user.getId());

        postService.create(request1);
        postService.create(request2);

        List<PostDtoResponse> posts = new ArrayList<>(postService.findAll(10, 0, "ASC"));
        List<PostDtoResponse> post = new ArrayList<>(postService.findAll(1, 0, "DESC"));

        assertThat(posts.size(), equalTo(2));
        assertThat(posts.getLast().getDescription(), equalTo(request2.getDescription()));

        assertThat(post.getFirst().getDescription(), equalTo(request1.getDescription()));
    }

    @Test
    void testPostAndGetComment() {
        User user = userRepository.save(testUser);

        request1.setAuthorId(user.getId());
        request2.setAuthorId(user.getId());

        Post post1 = postRepository.save(postMapper.mapPojo(request1));
        Post post2 = postRepository.save(postMapper.mapPojo(request2));

        postService.createComment(post1.getId(), commRequest1);
        postService.createComment(post2.getId(), commRequest2);

        List<CommentDtoResponse> post1Comment = new ArrayList<>(postService.getComments(LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5), post1.getId()));
        List<CommentDtoResponse> post2Comment = new ArrayList<>(postService.getComments(LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5), post2.getId()));

        assertThat(post1Comment.getFirst().getPost(), equalTo(post1));
        assertThat(post2Comment.getFirst().getDate(), equalTo(LocalDate.now()));
    }
}