package com.music.suffer.upload.service.implementation;

import com.music.suffer.upload.model.Image;
import com.music.suffer.upload.model.Song;
import com.music.suffer.upload.repository.ImageRepository;
import com.music.suffer.upload.repository.SongRepository;
import com.music.suffer.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {
    @Value("${upload.img}")
    private String imgPath;
    @Value("${upload.audio}")
    private String audioPath;

    private final ImageRepository imageRepository;
    private final SongRepository songRepository;

    @Autowired
    public UploadServiceImpl(ImageRepository ir, SongRepository sr) {
        imageRepository = ir;
        songRepository = sr;
    }

    public void uploadFile(MultipartFile file, String nameWithUUID, String type) {

        File destination = null;
        String path = null;

        if (type.equals("img")){
            destination = new File(imgPath);
            path = imgPath;
        } else if (type.equals("audio")) {
            destination = new File(audioPath);
            path = audioPath;
        }
            if (!destination.exists()) {
                destination.mkdir();
            }
            path += "/" + nameWithUUID;
            try {
                file.transferTo(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveUploadToDb(nameWithUUID, type);
    }

    private void saveUploadToDb(String name, String type) {
        if (type.equals("img")) {
            Image image = new Image(name);
            imageRepository.save(image);
        }
        else if (type.equals("audio")) {
            Song song = new Song(name);
            songRepository.save(song);
        }
    }
}
