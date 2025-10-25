package com.example.crud_sample.mapper;

import com.example.crud_sample.dto.request.PostCreateRequest;
import com.example.crud_sample.model.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toEntity(PostCreateRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        return post;
    }
}
