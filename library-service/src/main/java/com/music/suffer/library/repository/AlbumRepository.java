package com.music.suffer.library.repository;

import com.music.suffer.library.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findAll(Pageable pageable);

    List<Album> findByName(String name);

    List<Album> findByArtistId(Long artistId);

    @Query("SELECT a FROM Album a WHERE a.name LIKE CONCAT('%',:request,'%')")
    Page<Album> findByNameLike(@Param("request") String request, Pageable pageable);
}
