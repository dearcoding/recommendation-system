package com.dearcoding.recommendationsystem.controller;

import com.dearcoding.recommendationsystem.controller.v1.MovieController;
import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Test
    public void testListMoviesWithParameters() {
        String genre = "Action";
        Double minRating = 7.0;
        Double maxRating = 9.0;
        List<MovieDTO> expectedMovies = Arrays.asList(new MovieDTO(), new MovieDTO());
        when(movieService.getMovies(genre, minRating, maxRating)).thenReturn(expectedMovies);

        List<MovieDTO> actualMovies = movieController.list(genre, minRating, maxRating);

        assertEquals(expectedMovies, actualMovies);
        verify(movieService).getMovies(genre, minRating, maxRating);
    }

    @Test
    public void testSearchMoviesWithParameters() {
        String title = "Toy Story";
        String genres = "Adventure,Animation";
        String keywords = "toy,story";

        List<String> expectedGenreList = Arrays.asList("Advenure", "Animation");
        List<String> expectedKeywordList = Arrays.asList("toy", "story");
        List<MovieDTO> expectedMovies = Collections.singletonList(new MovieDTO());

        when(movieService.searchMovies(title, expectedGenreList, expectedKeywordList))
                .thenReturn(expectedMovies);

        List<MovieDTO> actualMovies = movieController.search(title, genres, keywords);

        assertEquals(expectedMovies, actualMovies);
        verify(movieService).searchMovies(title, expectedGenreList, expectedKeywordList);
    }

    @Test
    public void testSearchMoviesWithNullParameters() {
        List<MovieDTO> expectedMovies = Collections.emptyList();
        when(movieService.searchMovies(null, Collections.emptyList(), Collections.emptyList()))
                .thenReturn(expectedMovies);

        List<MovieDTO> actualMovies = movieController.search(null, null, null);

        assertEquals(expectedMovies, actualMovies);
        verify(movieService).searchMovies(null, Collections.emptyList(), Collections.emptyList());
    }
}
