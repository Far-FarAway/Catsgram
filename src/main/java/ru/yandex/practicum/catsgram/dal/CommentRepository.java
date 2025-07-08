package ru.yandex.practicum.catsgram.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.catsgram.model.Comment;

import java.time.LocalDate;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT comm FROM Comment comm " +
            "WHERE comm.date >= ?1 AND comm.date <= ?2")
    List<Comment> findAllByFilters(LocalDate from, LocalDate until);

    @Query("SELECT comm FROM Comment comm " +
            "WHERE comm.date >= ?1")
    List<Comment> findAllByFilterFrom(LocalDate from);

    @Query("SELECT comm FROM Comment comm " +
            "WHERE comm.date <= ?1")
    List<Comment> findAllByFilterUntil(LocalDate until);

    List<Comment> findByPostId(Long id);
}
