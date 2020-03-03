package com.music.suffer.library.controller;

import com.music.suffer.library.model.Artist;
import com.music.suffer.library.repository.ArtistRepository;
import com.music.suffer.library.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/artist/{id}")
@RequiredArgsConstructor
public class ArtistController {
    private final MusicService musicService;
    private final ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Artist getArtist(@PathVariable("id") Artist artist) {
        return artist;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Artist saveEditArtist(
            @PathVariable("id") Artist artist,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String year,
            @RequestParam(required = false) MultipartFile file
    ) {
        if (!musicService.updateArtist(artist, name, description, year, file)) {
            System.out.println("Unable to save changes!");
            return null;
        }
        return artist;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteArtist(@PathVariable("id") Artist artist) {
        artistRepository.delete(artist);
    }
}
