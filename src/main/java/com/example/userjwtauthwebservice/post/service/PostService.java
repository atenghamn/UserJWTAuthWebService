package com.example.userjwtauthwebservice.post.service;

import com.example.userjwtauthwebservice.post.entity.Post;
import com.example.userjwtauthwebservice.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getAll(int id){
        return postRepository.findAllById(id);
    }
}
