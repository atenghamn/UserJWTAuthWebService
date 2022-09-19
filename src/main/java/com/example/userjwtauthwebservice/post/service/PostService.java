package com.example.userjwtauthwebservice.post.service;

import com.example.userjwtauthwebservice.post.dto.PostDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final WebClient webClient;

    public List<PostDetail> getAll(int id){
        return webClient
                .get()
                .uri(String.format("/posts?userId=%s", id))
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(PostDetail.class))
                .buffer()
                .blockLast();
    }
}