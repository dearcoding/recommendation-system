package com.dearcoding.recommendationsystem.mapper;

import com.dearcoding.recommendationsystem.dto.rating.RatingItemDTO;
import com.dearcoding.recommendationsystem.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "movieId", source="movie.id")
    RatingItemDTO ratingToRatingItemDTO(Rating rating);
}
