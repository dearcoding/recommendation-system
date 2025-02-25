package com.dearcoding.recommendationsystem.service;

import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.dto.user.AddUserEventDTO;
import com.dearcoding.recommendationsystem.dto.user.UserDTO;
import com.dearcoding.recommendationsystem.model.enums.HistoryReturnType;

import java.util.List;

public interface UserService {
    HistoryDTO getHistory(Long userId, HistoryReturnType returnType);

    UserDTO addEvent(Long userId, AddUserEventDTO addUserEventDTO);

    List<MovieDTO> getRecommendations(Long userId);
}
