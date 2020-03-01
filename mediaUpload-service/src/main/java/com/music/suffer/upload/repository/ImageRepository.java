package com.music.suffer.upload.repository;

import com.music.suffer.upload.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
