package com.music.suffer.library.service.implementation;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Artist;
import com.music.suffer.library.model.Genre;
import com.music.suffer.library.model.Song;
import com.music.suffer.library.repository.AlbumRepository;
import com.music.suffer.library.repository.ArtistRepository;
import com.music.suffer.library.repository.SongRepository;
import com.music.suffer.library.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {
    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;
    private SongRepository songRepository;

    @Autowired
    public MusicServiceImpl(ArtistRepository artistRepository,
                            AlbumRepository albumRepository,
                            SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }


    @Override
    public boolean addArtist(String name, String description, String year, MultipartFile file) {
        Artist artistFromDb = artistRepository.findByName(name);
        if (artistFromDb != null) {
            return false;
        }
        Artist artist = new Artist();
        artist.setName(name);
        artist.setDescription(description);
        artist.setYear(year);

        String imageName = makePath(file);
        if (imageName == null) {
            return false;
        }
        artist.setImagePath(imageName);
        uploadFile(file, imageName, "img");

        artistRepository.save(artist);
        return true;
    }

    @Override
    public boolean addAlbum(String name, String artistName, MultipartFile cover, Genre genre) {
        List<Album> albumByName = albumRepository.findByName(name);
        Artist artist = artistRepository.findByName(artistName);

        Optional<Album> optional = albumByName.stream()
                .filter(a -> a.getArtist().getName().equals(artistName))
                .findFirst();
        if (artist == null || optional.isPresent()) {
            return false;
        }
        System.out.println("Create");
        Album album = new Album();
        album.setName(name);
        album.setArtist(artist);
        album.setGenre(genre);
        System.out.println("Saving file");

        String coverName = makePath(cover);
        if (coverName == null) {
            return false;
        }
        album.setCoverPath(coverName);
        uploadFile(cover, coverName, "img");

        System.out.println("DB");
        albumRepository.save(album);

        return true;
    }

    @Override
    public boolean updateAlbum(Album album, String name, String artistName, MultipartFile cover, Genre genre) {
        album.setName(name);
        Artist artist = artistRepository.findByName(artistName);
        if (artist == null) {
            return false;
        }
        album.setArtist(artist);
        album.setGenre(genre);

        String coverName = makePath(cover);
        if (coverName == null) {
            return false;
        }
        album.setCoverPath(coverName);
        uploadFile(cover, coverName, "img");

        albumRepository.save(album);
        return true;
    }

    @Override
    public boolean updateArtist(Artist artist, String name, String description, String year, MultipartFile file) {
        artist.setName(name);
        artist.setDescription(description);
        artist.setYear(year);

        String imageName = makePath(file);
        if (imageName == null) {
            return false;
        }
        artist.setImagePath(imageName);
        uploadFile(file, imageName, "img");

        artistRepository.save(artist);
        return true;
    }

    //тут я тоже переделал, теперь добавляется не только название, но и сам аудио файл
    @Override
    public boolean addSong(Album album, String name, MultipartFile file) {
        List<Song> songs = album.getSongs();
        Song song = new Song();
        song.setAlbum(album);
        song.setName(name);
        song.setNumber(songs.size() + 1);
        songs.add(song);
        songRepository.save(song);

        String audioName = makePath(file);
        if (audioName == null) {
            return false;
        }
        uploadFile(file, audioName, "audio");

        return true;
    }

    @Override
    public void deleteSong(Album album, Song song) {
        songRepository.delete(song);
        album.getSongs().remove(song);
    }

    private String makePath(MultipartFile file) {
        if (!file.isEmpty()) {
            String originalName = UUID.randomUUID().toString()
                    .concat(".").concat(file.getOriginalFilename());
            if (originalName.equals("")) {
                return null;
            }
            return originalName;
        }
        return null;
    }
}
