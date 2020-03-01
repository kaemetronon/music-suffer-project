package com.music.suffer.library.repository;

import com.music.suffer.library.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
