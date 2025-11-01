package com.example.crud_sample.service;

import com.example.crud_sample.model.entity.Post;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.repository.PostRepository;
import com.example.crud_sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPostForUser(Long userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }

    public Page<Post> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    public List<Post> getPostsOfTodayByUser(Long userId) {
        LocalDate today = LocalDate.now();

        return postRepository.findByUserIdAndCreatedDateBetween(
                userId,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );
    }

    public boolean deletePostForUser(Long userId, Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent() && postOptional.get().getUser().getId().equals(userId)) {
            postRepository.delete(postOptional.get());
            return true;
        }
        return false;
    }
}
