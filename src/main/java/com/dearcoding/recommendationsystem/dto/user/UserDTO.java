package com.dearcoding.recommendationsystem.dto.user;

import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private HistoryDTO history;
}
