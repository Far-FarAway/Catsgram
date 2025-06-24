package ru.yandex.practicum.catsgram.model;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;
    @Column
    String description;
    @Column(name = "likes_count")
    long likesCount;
    @Column(name = "comment_date")
    LocalDate date;
}
