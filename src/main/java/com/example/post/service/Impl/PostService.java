package com.example.post.service.Impl;

import com.example.post.model.Post;
import com.example.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService implements com.example.post.service.IPostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> findByTitle(String title, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        return postRepository.findByTitleAndCreateAt(title, dateFrom, dateTo, pageable);
    }

    @Override
    public Iterable<Post> findAllByOrOrderByLikes() {
        return postRepository.findAllPostOrderByLikesDesc();
    }

    @Override
    public Iterable<Post> findTopByCreateAt() {
        return postRepository.findTop4PostNewest();
    }

}
