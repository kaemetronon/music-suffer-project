package com.music.suffer.library.service;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Comment;
import com.music.suffer.library.model.User;

public interface CommentService {
    boolean addComment(Album album, User user, String text, String mark);

    void deleteComment(Album album, Long commentId);

    boolean editComment(Album album, Comment comment, String text, String mark);
}
