package com.dearcoding.recommendationsystem.mapper;

import com.dearcoding.recommendationsystem.dto.view.ViewItemDTO;
import com.dearcoding.recommendationsystem.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViewMapper {

    @Mapping(target = "movieId", source="movie.id")
    ViewItemDTO ratingToViewItemDTO(Rating rating);

}
