package ru.yandex.practicum.catsgram.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM posts " +
            "ORDER BY post_date DESC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Post> findAllByFiltersAndOrderDesc(int size, int from);

    @Query(value = "SELECT * FROM posts " +
            "ORDER BY post_date ASC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Post> findAllByFiltersAndOrderAsc(int size, int from);
}
