package com.music.suffer.library.controller;

import com.music.suffer.library.model.Album;
import com.music.suffer.library.model.Artist;
import com.music.suffer.library.repository.ArtistRepository;
import com.music.suffer.library.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainPageService mainPageService;
    private final ArtistRepository artistRepository;

    //если это все рест конроллеры, то как они верну страницу типа модели?
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String greetings() {
        return "greetings";
    }

    //пока хз что писать ниже
    @RequestMapping(method = RequestMethod.GET, value = "/main")
    public String main(
            Model model,
            String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 9) Pageable pageable
    ) {
        Page<Album> albums;
        if (StringUtils.isEmpty(filter)) {
            albums = mainPageService.getAll(pageable);
        } else {
            albums = mainPageService.getByQuery(filter, pageable);
        }
        model.addAttribute("url", "/main");
        model.addAttribute("albums", albums);
        model.addAttribute("filter", filter);
        return "main";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/artists")
    public List<Artist> artists(String filter) {
        Iterable<Artist> artists;
        if (StringUtils.isEmpty(filter)) {
            artists = mainPageService.getAllArtists();
        } else {
            artists = mainPageService.getArtistByQuery(filter);
        }
        return StreamSupport.stream(artists.spliterator(), false)
                .collect(Collectors.toList());
    }
}
