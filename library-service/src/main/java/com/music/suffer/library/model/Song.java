package com.music.suffer.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer number;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private Album album;
}
