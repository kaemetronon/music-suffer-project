package com.music.suffer.library.repository;

import com.music.suffer.library.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAlbumId(Long albumId);
}
