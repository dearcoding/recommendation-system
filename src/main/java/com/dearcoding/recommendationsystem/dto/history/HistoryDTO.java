package com.dearcoding.recommendationsystem.dto.history;

import com.dearcoding.recommendationsystem.dto.rating.RatingItemDTO;
import com.dearcoding.recommendationsystem.dto.view.ViewItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {

    private List<RatingItemDTO> ratings;
    private List<ViewItemDTO> views;
}
