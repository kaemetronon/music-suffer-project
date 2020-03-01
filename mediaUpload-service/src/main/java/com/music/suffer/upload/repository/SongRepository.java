package com.music.suffer.upload.repository;

import com.music.suffer.upload.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}
