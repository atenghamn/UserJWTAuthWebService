package com.example.userjwtauthwebservice.post.entity;

import com.example.userjwtauthwebservice.user.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    int id;

    @Column(name="userId")
    @NotNull
    @JsonIgnore
    int userId;

    @Column
    String title;

    @Column
    String body;


}
