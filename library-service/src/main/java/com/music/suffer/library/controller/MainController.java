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

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainPageService mainPageService;
    private final ArtistRepository artistRepository;

    @Autowired
    public MainController(MainPageService mps, ArtistRepository ar) {
        mainPageService = mps;
        artistRepository = ar;
    }

    //ну а в этом классе все методы такие, я хз что с ними делать
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String greetings(Model model) {
        return "greetings";
    }

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
    public String artists(Model model, String filter) {
        Iterable<Artist> artists;
        if (StringUtils.isEmpty(filter)) {
            artists = mainPageService.getAllArtists();
        } else {
            artists = mainPageService.getArtistByQuery(filter);
        }
        model.addAttribute("artists", artists);
        model.addAttribute("filter", filter);
        return "artists-list";
    }
}
