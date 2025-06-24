package ru.yandex.practicum.catsgram.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = {"email"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;
    @Column
    String username;
    @Column
    String email;
    @Column
    String password;
    @Column(name = "registration_date")
    Instant registrationDate;
}
