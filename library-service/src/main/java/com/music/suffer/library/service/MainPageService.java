package com.music.suffer.library.service;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MainPageService {
    Page<Album> getAll(Pageable pageable);

    Page<Album> getByQuery(String request, Pageable pageable);

    List<Artist> getAllArtists();

    List<Artist> getArtistByQuery(String request);
}
