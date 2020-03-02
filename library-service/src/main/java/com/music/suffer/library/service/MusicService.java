package com.music.suffer.library.service;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Artist;
import com.music.suffer.library.model.Genre;
import com.music.suffer.library.model.Song;
import org.springframework.web.multipart.MultipartFile;

public interface MusicService {
    boolean addArtist(String name, String description, String year, MultipartFile file);

    boolean addAlbum(String name, String artistName, MultipartFile cover, Genre genre);

    boolean updateAlbum(Album album, String name, String artistName, MultipartFile cover, Genre genre);

    boolean updateArtist(Artist artist, String name, String description, String year, MultipartFile file);

    boolean addSong(Album album, String song, MultipartFile file);

    void deleteSong(Album album, Song song);
}
