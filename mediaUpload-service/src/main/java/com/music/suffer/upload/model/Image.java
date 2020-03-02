package com.music.suffer.upload.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "image_paths")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    @NotNull
    private String imagePath;
}
