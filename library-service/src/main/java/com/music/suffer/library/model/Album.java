package com.music.suffer.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String coverPath;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Comment> comments;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;

    private Double averageScore = 0.0;
}
