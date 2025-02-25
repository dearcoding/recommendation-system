package com.dearcoding.recommendationsystem.mapper;

import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO movieToMovieDTO(Movie movie);
    List<MovieDTO> moviesToMovieDTOs(List<Movie> movies);

}
