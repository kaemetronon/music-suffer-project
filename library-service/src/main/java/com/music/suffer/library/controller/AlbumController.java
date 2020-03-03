package com.music.suffer.library.controller;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Comment;
import com.music.suffer.library.model.User;
import com.music.suffer.library.repository.CommentRepository;
import com.music.suffer.library.service.CommentService;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/album/{id}")
@RequiredArgsConstructor
public class AlbumController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final EurekaClient client;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    @Transactional
    public Album getAlbum(@PathVariable("id") Album album) {
        return album;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addComment")
    public boolean addComment(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Album album,
            String text,
            String mark
    ) {
        if (!commentService.addComment(album, user, text, mark)) {
            System.out.println("Unable to add comment... Try again.");
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comment/{commentId}/delete")
    public void deleteComment(@PathVariable("id") Album album, @PathVariable Long commentId) {
        commentService.deleteComment(album, commentId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comment/{commentId}/edit")
    public Comment saveEditComment(
            @PathVariable("id") Album album,
            @PathVariable("commentId") Comment comment,
            String text,
            String mark
    ) {
        if (!commentService.editComment(album, comment, text, mark)) {
            System.out.println("Unable to add comment... Try again.");
            return null;
        }
        return comment;
    }
}
