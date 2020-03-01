package com.music.suffer.library.controller;

import com.music.suffer.library.model.Artist;
import com.music.suffer.library.repository.ArtistRepository;
import com.music.suffer.library.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/artist/{id}")
@RequiredArgsConstructor
public class ArtistController {
    private final MusicService musicService;
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistController(MusicService ms, ArtistRepository ar) {
        musicService = ms;
        artistRepository = ar;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Artist getArtist(Model model, @PathVariable("id") Artist artist) {
        return artist;
//        model.addAttribute("artist", artist);
//        return "artist";
    }

    //про этот метод в частности я писал в readMe
    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editArtist(Model model, @PathVariable("id") Artist artist) {
//        model.addAttribute("artist", artist);
//        return "artist-edit";
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Artist saveEditArtist(
            Model model,
            @PathVariable("id") Artist artist,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String year,
            @RequestParam(required = false) MultipartFile file
    ) {
        if (!musicService.updateArtist(artist, name, description, year, file)) {
            System.out.println("Unable to save changes!");
            return null;
//            model.addAttribute("message", "Unable to save changes!");
        }
        return artist;
//        model.addAttribute("artist", artist);
//        return "artist-edit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteArtist(Model model, @PathVariable("id") Artist artist) {
        artistRepository.delete(artist);
//        model.addAttribute("artists", artistRepository.findAll());
//        model.addAttribute("filter", "");
//        return "artists-list";
    }
}
