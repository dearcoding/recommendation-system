package com.dearcoding.recommendationsystem.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewItemDTO {

    private Long movieId;
    private Integer viewPercentage;

}
