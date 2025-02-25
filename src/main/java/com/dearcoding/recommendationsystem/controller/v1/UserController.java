package com.dearcoding.recommendationsystem.controller.v1;

import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.dto.user.AddUserEventDTO;
import com.dearcoding.recommendationsystem.dto.user.UserDTO;
import com.dearcoding.recommendationsystem.model.enums.HistoryReturnType;
import com.dearcoding.recommendationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/history")
    public HistoryDTO history(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "ALL") HistoryReturnType returnType) {
        return userService.getHistory(userId, returnType);
    }

    @PostMapping("/{userId}/addEvent")
    public UserDTO addEvent(
            @PathVariable Long userId,
            @RequestBody AddUserEventDTO addUserEventDTO) {
        return userService.addEvent(userId, addUserEventDTO);
    }

    @GetMapping("/{userId}/recommendations")
    public List<MovieDTO> recommendations(
            @PathVariable Long userId) {
        return userService.getRecommendations(userId);
    }

}
