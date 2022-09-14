package com.example.userjwtauthwebservice.post.dto;

import com.example.userjwtauthwebservice.post.entity.Post;
import com.example.userjwtauthwebservice.user.dto.UserDetail;

import java.util.List;

public final class PostDetailMapper {

    private PostDetailMapper(){
        // util class
    }

    public static List<PostDetail> from (List<Post> entities){
        return entities.stream()
                .map(PostDetailMapper::from)
                .toList();
    }

    public static PostDetail from (Post entity){
        return new PostDetail(
                entity.getId(),
                entity.getUserId().getId(),
                entity.getTitle(),
                entity.getTitle()
        );
    }

}
