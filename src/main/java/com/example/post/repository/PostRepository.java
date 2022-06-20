package com.example.post.repository;

import com.example.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Iterable<Post> findPostByTitleContaining(String title);
    @Query(value = "from Post order by likes asc")
    Iterable<Post> findAllPostOrderByLikesDesc();
    @Query(value = "select * from post order by createAt desc limit 4" ,nativeQuery = true)
    Iterable<Post> findTop4PostNewest();
    @Query(value = "from Post where title like :title and createAt between :dateFrom and :dateTo")
    Iterable<Post> findByTitleAndCreateAt(@Param("title") String title, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo")LocalDateTime dateTo);
}
