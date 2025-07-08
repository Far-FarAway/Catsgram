package ru.yandex.practicum.catsgram.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.UserDtoRequest;
import ru.yandex.practicum.catsgram.dto.UserDtoResponse;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    @Autowired
    UserService userService;
    UserDtoRequest user1;

    @BeforeEach
    void before() {
        user1 = UserDtoRequest.builder()
                .email("fhoshfo@gmail.com")
                .username("billy")
                .password("test")
                .build();
    }

    @Test
    void testPutUser() {
        Mockito.when(userRepository.findById(Mockito.any(Long.class)))
                        .thenReturn(Optional.of(User.builder().id(3213L).build()));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserDtoResponse user = userService.putUser(3213L, user1);

        assertThat(user.getRegistrationDate(), notNullValue());
        assertThat(user.getUsername(), equalTo(user1.getUsername()));
    }
}