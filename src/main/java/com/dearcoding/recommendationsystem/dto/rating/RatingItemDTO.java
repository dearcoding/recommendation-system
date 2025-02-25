package com.dearcoding.recommendationsystem.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingItemDTO {

    private Long movieId;
    private Integer rating;
}
