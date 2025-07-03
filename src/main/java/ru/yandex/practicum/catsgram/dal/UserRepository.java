package ru.yandex.practicum.catsgram.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE LOWER(u.username) = LOWER(?1) OR LOWER(u.email) = LOWER(?1)")
    Optional<User> findDuplicate(String string);
}
