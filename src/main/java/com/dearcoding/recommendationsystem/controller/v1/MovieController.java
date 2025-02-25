package com.dearcoding.recommendationsystem.controller.v1;

import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDTO> list(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating) {
        return movieService.getMovies(genre, minRating, maxRating);
    }


    @GetMapping("/search")
    public List<MovieDTO> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genres,
            @RequestParam(required = false) String keywords) {
        List<String> genreList = genres != null ? List.of(genres.split(",")) : List.of();
        List<String> keywordList = keywords != null ? List.of(keywords.split(",")) :List.of();
        return movieService.searchMovies(title, genreList, keywordList);
    }

}
