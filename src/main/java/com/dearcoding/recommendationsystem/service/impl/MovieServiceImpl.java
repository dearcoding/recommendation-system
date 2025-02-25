package com.dearcoding.recommendationsystem.service.impl;

import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.entity.Movie;
import com.dearcoding.recommendationsystem.mapper.MovieMapper;
import com.dearcoding.recommendationsystem.repository.MovieRepository;
import com.dearcoding.recommendationsystem.service.MovieService;
import com.dearcoding.recommendationsystem.specification.MovieSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;


    @Override
    public List<MovieDTO> getMovies(String genre, Double minRating, Double maxRating){
        List<Movie> movies = movieRepository.findMoviesWithFilters(genre, minRating, maxRating);
        return movieMapper.moviesToMovieDTOs(movies);
    }

    public List<MovieDTO> searchMovies(String title, List<String> genres, List<String> keywords) {
        Specification<Movie> spec = MovieSpecification.buildMovieSpecification(title, genres, keywords);
        List<Movie> movies = movieRepository.findAll(spec);
        return movieMapper.moviesToMovieDTOs(movies);
    }
}
