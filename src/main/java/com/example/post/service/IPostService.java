package com.example.post.service;

import com.example.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IPostService extends GeneralService<Post> {
    Page<Post> findByTitle(String title, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
    Iterable<Post> findAllByOrOrderByLikes();
    Iterable<Post> findTopByCreateAt();
}
