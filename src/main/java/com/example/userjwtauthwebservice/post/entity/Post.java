package com.example.userjwtauthwebservice.post.entity;

import com.example.userjwtauthwebservice.user.entities.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    int Id;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    User userId;

    @Column
    String title;

    @Column
    String body;


}
