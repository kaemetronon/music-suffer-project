package com.music.suffer.library.controller;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Comment;
import com.music.suffer.library.model.User;
import com.music.suffer.library.repository.CommentRepository;
import com.music.suffer.library.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/album/{id}")
@RequiredArgsConstructor
public class AlbumController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public AlbumController(CommentRepository cr, CommentService cs) {
        this.commentRepository = cr;
        this.commentService = cs;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/")
    @Transactional
    public Album getAlbum(Model model, @PathVariable("id") Album album) {
        return album;
//        // -что было в оригинале
//        model.addAttribute("album", album);
//        model.addAttribute("comments", commentRepository.findAllByAlbumId(album.getId()));
//        return "album";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addComment")
    public boolean addComment(
            Model model,
            @AuthenticationPrincipal User user,
            @PathVariable("id") Album album,
            String text,
            String mark
    ) {
        if (!commentService.addComment(album, user, text, mark)) {
            System.out.println("Unable to add comment... Try again.");
            return false;
//            model.addAttribute("error", "Unable to add comment... Try again.");
        }
        return true;
//        model.addAttribute("album", album);
//        model.addAttribute("comments", commentRepository.findAllByAlbumId(album.getId()));
//        return "album";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comment/{commentId}/delete")
    public void deleteComment(Model model, @PathVariable("id") Album album, @PathVariable Long commentId) {
        commentService.deleteComment(album, commentId);
//        model.addAttribute("album", album);
//        model.addAttribute("comments", commentRepository.findAllByAlbumId(album.getId()));
//        return "album";
    }

    //про этот метод в частности я писал в readMe
    @RequestMapping(method = RequestMethod.GET, value = "/comment/{commentId}/edit")
    public String editComment(Model model, @PathVariable("commentId") Comment comment) {
//        model.addAttribute("comment", comment);
//        return "comment-edit";
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comment/{commentId}/edit")
    public Comment saveEditComment(
            Model model,
            @PathVariable("id") Album album,
            @PathVariable("commentId") Comment comment,
            String text,
            String mark
    ) {
        if (!commentService.editComment(album, comment, text, mark)) {
            System.out.println("Unable to add comment... Try again.");
            return null;
//            model.addAttribute("error", "Unable to add comment... Try again.");
        }
        return comment;
//        model.addAttribute("album", album);
//        model.addAttribute("comments", commentRepository.findAllByAlbumId(album.getId()));
//        return "album";
    }
}
