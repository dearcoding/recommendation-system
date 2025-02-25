package com.dearcoding.recommendationsystem.service;

import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getMovies(String genre, Double minRating, Double maxRating);

    List<MovieDTO> searchMovies(String title, List<String> genres, List<String> keywords);
}
