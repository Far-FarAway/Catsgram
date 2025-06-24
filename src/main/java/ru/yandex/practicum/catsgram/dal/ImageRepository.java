package ru.yandex.practicum.catsgram.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.catsgram.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long id);
}
