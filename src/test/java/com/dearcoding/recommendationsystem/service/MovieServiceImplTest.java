package com.dearcoding.recommendationsystem.service;

import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.entity.Movie;
import com.dearcoding.recommendationsystem.mapper.MovieMapper;
import com.dearcoding.recommendationsystem.repository.MovieRepository;
import com.dearcoding.recommendationsystem.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovies() {
        Long id = 1L;
        String title = "Toy Story";
        String genre = "Adventure";
        Double minRating = 7.0;
        Double maxRating = 10.0;

        Movie movie = Movie.builder()
                .id(id)
                .title(title)
                .genres(List.of(genre))
                .build();
        List<Movie> movies = List.of(movie);

        MovieDTO movieDTO = MovieDTO.builder()
                .id(id)
                .title(title)
                .genres(List.of(genre))
                .build();
        List<MovieDTO> movieDTOs = List.of(movieDTO);

        when(movieRepository.findMoviesWithFilters(genre, minRating, maxRating)).thenReturn(movies);
        when(movieMapper.moviesToMovieDTOs(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getMovies(genre, minRating, maxRating);

        assertEquals(movieDTOs, result);
        verify(movieRepository).findMoviesWithFilters(genre, minRating, maxRating);
        verify(movieMapper).moviesToMovieDTOs(movies);
    }

    @Test
    void testSearchMovies() {
        String title = "Toy Story";
        List<String> genres = Arrays.asList("Adventure", "Animation");
        List<String> keywords = Arrays.asList("toy", "story");

        Movie movie = Movie.builder()
                .id(1L)
                .genres(genres)
                .title(title)
                .build();
        List<Movie> movies = Collections.singletonList(movie);

        MovieDTO movieDTO = MovieDTO.builder()
                .id(1L)
                .genres(genres)
                .title(title)
                .build();
        List<MovieDTO> movieDTOs = Collections.singletonList(movieDTO);

        when(movieRepository.findAll(any(Specification.class))).thenReturn(movies);
        when(movieMapper.moviesToMovieDTOs(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.searchMovies(title, genres, keywords);

        assertEquals(movieDTOs, result);
        verify(movieRepository).findAll(any(Specification.class));
        verify(movieMapper).moviesToMovieDTOs(movies);
    }
}
