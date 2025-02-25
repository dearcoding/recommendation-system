package com.dearcoding.recommendationsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserEventDTO {

    private Long movieId;

    private Integer rating;

    private Integer viewPercentage;
}
