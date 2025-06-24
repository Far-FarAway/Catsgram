package ru.yandex.practicum.catsgram.model;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "image_storage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
    @Column(name = "original_name")
    String originalFileName;
    @Column(name = "file_path")
    String filePath;
}
