package com.music.suffer.library.controller;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Genre;
import com.music.suffer.library.model.Song;
import com.music.suffer.library.repository.AlbumRepository;
import com.music.suffer.library.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/music-manage")
@RequiredArgsConstructor
public class MusicManagmentController {
    private final MusicService musicService;
    private final AlbumRepository albumRepository;

//
//    @RequestMapping(method = RequestMethod.GET, value = "/")
//    public String getManagePage(Model model) {
//        model.addAttribute("genres", Genre.values());
//        return "music-manage";
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/addArtist")
    public boolean addArtist(
            String name,
            String description,
            String year,
            @RequestParam(name = "image", required = false) MultipartFile image
    ) {
        if (!musicService.addArtist(name, description, year, image)) return false;
        else return true;
    }

    //в оригинале тут в модель кладется список ролей. мб просто возвращать этот список, а не булеан
    @RequestMapping(method = RequestMethod.POST, value = "/addAlbum")
    public boolean addAlbum(
            @RequestParam String name,
            @RequestParam String artistName,
            @RequestParam Genre genre,
            @RequestParam("file") MultipartFile cover
    ) {
        System.out.println(name + " " + artistName);
        if (!musicService.addAlbum(name, artistName, cover, genre)) return false;
        else return true;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/album/{id}")
//    @Transactional
//    public String getAlbumEdit(Model model, @PathVariable("id") Album album) {
//        model.addAttribute("genres", Genre.values());
//        model.addAttribute("album", album);
//        return "album-edit";
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/album/{id}/delete")
    public void deleteAlbum(@PathVariable("id") Album album) {
        albumRepository.delete(album);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/album/{id}")
    @Transactional
    public boolean albumEdit(
            @RequestParam String name,
            @RequestParam String artistName,
            @RequestParam Genre genre,
            @RequestParam(value = "file", required = false) MultipartFile cover,
            @PathVariable("id") Album album
    ) {
        if (!musicService.updateAlbum(album, name, artistName, cover, genre))
            return false;
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/album/{id}/addSong")
    public boolean addSong(
            @PathVariable("id") Album album,
            @RequestParam String name,
            @RequestParam MultipartFile file
    ) {
        if (!musicService.addSong(album, name, file)) return false;
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/album/{id}/song/{songId}/delete")
    @Transactional
    public void deleteSong(
            @PathVariable("id") Album album,
            @PathVariable("songId") Song song
    ) {
        musicService.deleteSong(album, song);
    }
}
