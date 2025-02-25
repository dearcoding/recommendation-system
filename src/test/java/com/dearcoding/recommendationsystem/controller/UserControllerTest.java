package com.dearcoding.recommendationsystem.controller;

import com.dearcoding.recommendationsystem.controller.v1.UserController;
import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.dto.user.AddUserEventDTO;
import com.dearcoding.recommendationsystem.dto.user.UserDTO;
import com.dearcoding.recommendationsystem.model.enums.HistoryReturnType;
import com.dearcoding.recommendationsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testHistory() {
        Long userId = 1L;
        HistoryReturnType returnType = HistoryReturnType.ALL;
        HistoryDTO expectedHistory = new HistoryDTO();
        when(userService.getHistory(userId, returnType)).thenReturn(expectedHistory);

        HistoryDTO actualHistory = userController.history(userId, returnType);

        assertEquals(expectedHistory, actualHistory);
        verify(userService).getHistory(userId, returnType);
    }

    @Test
    public void testAddEvent() {
        Long userId = 1L;
        AddUserEventDTO addUserEventDTO = new AddUserEventDTO();
        UserDTO expectedUserDTO = new UserDTO();
        when(userService.addEvent(userId, addUserEventDTO)).thenReturn(expectedUserDTO);

        UserDTO actualUserDTO = userController.addEvent(userId, addUserEventDTO);

        assertEquals(expectedUserDTO, actualUserDTO);
        verify(userService).addEvent(userId, addUserEventDTO);
    }

    @Test
    public void testRecommendations() {
        Long userId = 1L;
        List<MovieDTO> expectedRecommendations = Arrays.asList(new MovieDTO(), new MovieDTO());
        when(userService.getRecommendations(userId)).thenReturn(expectedRecommendations);

        List<MovieDTO> actualRecommendations = userController.recommendations(userId);

        assertEquals(expectedRecommendations, actualRecommendations);
        verify(userService).getRecommendations(userId);
    }
}
