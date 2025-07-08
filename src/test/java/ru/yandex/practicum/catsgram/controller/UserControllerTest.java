package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    UserDtoRequest user1;

    @BeforeEach
    void before() {
        user1 = UserDtoRequest.builder()
                .email("test@gmail.com")
                .username("test")
                .password("t3st")
                .build();
    }

    @Test
    void testPostUserWithWrongEmail() throws Exception {
        user1.setEmail("fdfsdf");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user1)))
                .andExpect(status().is(400));
    }

    @Test
    void testPostUserWithEmptyUsername() throws Exception {
        user1.setUsername("");

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user1)))
                .andExpect(status().is(400));
    }

    @Test
    void testPostUserWithNullPassword() throws Exception {
        user1.setPassword(null);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user1)))
                .andExpect(status().is(400));
    }
}