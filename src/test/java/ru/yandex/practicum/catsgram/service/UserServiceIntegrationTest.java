package ru.yandex.practicum.catsgram.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.dto.UserDtoResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.datasource.url=jdbc:postgresql://localhost:5433/testDB",
        "spring.datasource.username=test",
        "spring.datasource.password=test"})
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceIntegrationTest {
    @Autowired
    UserService userService;
    UserDtoRequest user1 = UserDtoRequest.builder()
            .email("fhoshfo@gmail.com")
            .username("billy")
            .password("test")
            .build();
    UserDtoRequest user2 = UserDtoRequest.builder()
            .email("fhhfo@gmail.com")
            .username("billy2")
            .password("test2")
            .build();

    @Test
    void testSaveAndGetUsers() {
        userService.postUser(user1);
        userService.postUser(user2);

        List<UserDtoResponse> users = new ArrayList<>(userService.getUsers());

        assertThat(users.size(), equalTo(2));
        assertThat(users.getLast(), allOf(hasProperty("email"), hasProperty("username"),
                hasProperty("registrationDate")));
        assertThat(users.getLast().getUsername(), equalTo(user2.getUsername()));
        assertThat(users.getFirst().getRegistrationDate(), notNullValue());
    }
}