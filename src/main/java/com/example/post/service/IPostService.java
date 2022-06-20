package com.example.post.service;

import com.example.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IPostService extends GeneralService<Post> {
    Iterable<Post> findByTitle(String title, LocalDateTime dateFrom, LocalDateTime dateTo);
    Iterable<Post> findAllByOrOrderByLikes();
    Iterable<Post> findTopByCreateAt();
}
