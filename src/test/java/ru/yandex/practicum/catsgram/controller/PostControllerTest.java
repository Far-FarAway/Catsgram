package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.catsgram.dto.CommentDtoRequest;
import ru.yandex.practicum.catsgram.service.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    @MockBean
    PostService postService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;

    @Test
    void testPostCommentWithWrongDescription() throws Exception {
        CommentDtoRequest comment = CommentDtoRequest.builder()
                .description("")
                .build();

        mvc.perform(post("/posts/3/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().is(400));
    }
}