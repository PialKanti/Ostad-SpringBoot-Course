package com.example.crud_sample.repository;

import com.example.crud_sample.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.createdAt >= :start AND p.createdAt < :end")
    List<Post> findByUserIdAndCreatedDateBetween(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    Boolean existsByIdAndUserId(Long id, Long postId);
}
