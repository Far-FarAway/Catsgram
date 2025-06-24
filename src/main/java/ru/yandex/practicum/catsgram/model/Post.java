package ru.yandex.practicum.catsgram.model;


import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    User author;
    @Column
    String description;
    @Column(name = "post_date")
    LocalDate postDate;
    @OneToMany(mappedBy = "post")
    @MapKey(name = "id")
    Map<Long, Comment> comments;
}
