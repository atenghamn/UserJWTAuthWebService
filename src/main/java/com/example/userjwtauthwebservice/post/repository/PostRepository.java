package com.example.userjwtauthwebservice.post.repository;

import com.example.userjwtauthwebservice.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllById(int id);
}
