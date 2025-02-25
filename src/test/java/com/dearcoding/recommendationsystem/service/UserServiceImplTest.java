package com.dearcoding.recommendationsystem.service;

import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import com.dearcoding.recommendationsystem.dto.movie.MovieDTO;
import com.dearcoding.recommendationsystem.dto.rating.RatingItemDTO;
import com.dearcoding.recommendationsystem.dto.user.AddUserEventDTO;
import com.dearcoding.recommendationsystem.dto.user.UserDTO;
import com.dearcoding.recommendationsystem.dto.view.ViewItemDTO;
import com.dearcoding.recommendationsystem.entity.Movie;
import com.dearcoding.recommendationsystem.entity.Rating;
import com.dearcoding.recommendationsystem.entity.User;
import com.dearcoding.recommendationsystem.entity.composedKey.RatingId;
import com.dearcoding.recommendationsystem.mapper.MovieMapper;
import com.dearcoding.recommendationsystem.mapper.RatingMapper;
import com.dearcoding.recommendationsystem.mapper.UserMapper;
import com.dearcoding.recommendationsystem.mapper.ViewMapper;
import com.dearcoding.recommendationsystem.model.enums.HistoryReturnType;
import com.dearcoding.recommendationsystem.repository.MovieRepository;
import com.dearcoding.recommendationsystem.repository.RatingRepository;
import com.dearcoding.recommendationsystem.repository.UserRepository;
import com.dearcoding.recommendationsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private ViewMapper viewMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Movie movie;
    private Rating rating;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        movie = new Movie();
        movie.setId(10L);
        movie.setGenres(List.of("Adventure", "Animation"));

        rating = new Rating();
        RatingId ratingId = new RatingId();
        ratingId.setUserId(1L);
        ratingId.setMovieId(10L);
        rating.setId(ratingId);
        rating.setUser(user);
        rating.setMovie(movie);
        rating.setRating(4);
        rating.setViewPercentage(80);
    }

    @Test
    public void testGetHistoryViews() {
        when(ratingRepository.findByUser_IdAndViewPercentageIsNotNull(1L))
                .thenReturn(Arrays.asList(rating));
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        when(viewMapper.ratingToViewItemDTO(rating)).thenReturn(viewItemDTO);

        HistoryDTO history = userService.getHistory(1L, HistoryReturnType.VIEWS);

        verify(ratingRepository, times(1))
                .findByUser_IdAndViewPercentageIsNotNull(1L);
        assertNotNull(history);
        assertTrue(history.getViews().contains(viewItemDTO));
        assertTrue(history.getRatings().isEmpty());
    }

    @Test
    public void testGetHistoryRatings() {
        when(ratingRepository.findByUser_IdAndRatingIsNotNull(1L))
                .thenReturn(Arrays.asList(rating));
        RatingItemDTO ratingItemDTO = new RatingItemDTO();
        when(ratingMapper.ratingToRatingItemDTO(rating)).thenReturn(ratingItemDTO);

        HistoryDTO history = userService.getHistory(1L, HistoryReturnType.RATINGS);

        verify(ratingRepository, times(1))
                .findByUser_IdAndRatingIsNotNull(1L);
        assertNotNull(history);
        assertTrue(history.getRatings().contains(ratingItemDTO));
        assertTrue(history.getViews().isEmpty());
    }

    @Test
    public void testGetHistoryAll() {
        when(ratingRepository.findByUser_Id(1L))
                .thenReturn(List.of(rating));
        RatingItemDTO ratingItemDTO = new RatingItemDTO();
        ViewItemDTO viewItemDTO = new ViewItemDTO();

        when(ratingMapper.ratingToRatingItemDTO(rating)).thenReturn(ratingItemDTO);
        when(viewMapper.ratingToViewItemDTO(rating)).thenReturn(viewItemDTO);

        HistoryDTO history = userService.getHistory(1L, HistoryReturnType.ALL);

        verify(ratingRepository, times(1))
                .findByUser_Id(1L);
        assertNotNull(history);
        assertTrue(history.getRatings().contains(ratingItemDTO));
        assertTrue(history.getViews().contains(viewItemDTO));
    }

    @Test
    public void testAddEvent_UpdateExistingRating() {
        AddUserEventDTO eventDTO = new AddUserEventDTO();
        eventDTO.setMovieId(10L);
        eventDTO.setRating(3);
        eventDTO.setViewPercentage(90);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByUser_IdAndMovie_Id(1L, 10L))
                .thenReturn(Optional.of(rating));
        when(ratingRepository.save(rating)).thenReturn(rating);

        when(ratingRepository.findByUser_Id(1L)).thenReturn(List.of(rating));
        RatingItemDTO ratingItemDTO = new RatingItemDTO();
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        when(ratingMapper.ratingToRatingItemDTO(rating)).thenReturn(ratingItemDTO);
        when(viewMapper.ratingToViewItemDTO(rating)).thenReturn(viewItemDTO);

        UserDTO userDTO = new UserDTO();
        when(userMapper.userToUserDTO(eq(user), any(HistoryDTO.class))).thenReturn(userDTO);

        UserDTO result = userService.addEvent(1L, eventDTO);

        assertEquals(3, rating.getRating());
        assertEquals(90, rating.getViewPercentage());
        verify(ratingRepository, times(1)).save(rating);
        verify(userMapper, times(1)).userToUserDTO(eq(user), any(HistoryDTO.class));
        assertEquals(userDTO, result);
    }


    @Test
    public void testAddEvent_CreateNewRating() {
        AddUserEventDTO eventDTO = new AddUserEventDTO();
        eventDTO.setMovieId(10L);
        eventDTO.setRating(4);
        eventDTO.setViewPercentage(75);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByUser_IdAndMovie_Id(1L, 10L))
                .thenReturn(Optional.empty());

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        when(ratingRepository.save(any(Rating.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Rating newRating = new Rating();
        newRating.setUser(user);
        newRating.setMovie(movie);
        newRating.setRating(4);
        newRating.setViewPercentage(75);
        when(ratingRepository.findByUser_Id(1L))
                .thenReturn(List.of(newRating));
        RatingItemDTO ratingItemDTO = new RatingItemDTO();
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        when(ratingMapper.ratingToRatingItemDTO(newRating)).thenReturn(ratingItemDTO);
        when(viewMapper.ratingToViewItemDTO(newRating)).thenReturn(viewItemDTO);

        HistoryDTO historyDTO = HistoryDTO.builder()
                .ratings(Collections.singletonList(ratingItemDTO))
                .views(Collections.singletonList(viewItemDTO))
                .build();
        UserDTO userDTO = new UserDTO();
        when(userMapper.userToUserDTO(user, historyDTO)).thenReturn(userDTO);

        UserDTO result = userService.addEvent(1L, eventDTO);

        verify(ratingRepository, times(1)).save(ratingCaptor.capture());
        Rating savedRating = ratingCaptor.getValue();
        assertEquals(4, savedRating.getRating());
        assertEquals(75, savedRating.getViewPercentage());
        assertEquals(1L, savedRating.getUser().getId());
        assertEquals(10L, savedRating.getMovie().getId());
        verify(userMapper, times(1)).userToUserDTO(eq(user), any(HistoryDTO.class));
        assertEquals(userDTO, result);
    }

    @Test
    public void testAddEvent_UserNotFound() {
        AddUserEventDTO eventDTO = new AddUserEventDTO();
        eventDTO.setMovieId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userService.addEvent(1L, eventDTO));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testAddEvent_MovieNotFound() {
        AddUserEventDTO eventDTO = new AddUserEventDTO();
        eventDTO.setMovieId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userService.addEvent(1L, eventDTO));

        assertEquals("Movie not found", exception.getMessage());
    }

    @Test
    public void testGetRecommendations() {
        Movie likedMovie1 = new Movie();
        likedMovie1.setId(10L);
        likedMovie1.setGenres(Arrays.asList("Adventure", "Animation"));

        Movie likedMovie2 = new Movie();
        likedMovie2.setId(20L);
        likedMovie2.setGenres(List.of("Comedy"));

        when(movieRepository.findLikedByUserId(1L))
                .thenReturn(Arrays.asList(likedMovie1, likedMovie2));

        List<String> genres = List.of("Adventure", "Animation", "Comedy");
        Movie recommendedMovie = new Movie();
        recommendedMovie.setId(30L);
        when(movieRepository.findRecommendedByGenres(genres, 1L))
                .thenReturn(List.of(recommendedMovie));

        MovieDTO movieDTO = new MovieDTO();
        when(movieMapper.moviesToMovieDTOs(List.of(recommendedMovie)))
                .thenReturn(List.of(movieDTO));

        List<MovieDTO> result = userService.getRecommendations(1L);

        verify(movieRepository, times(1)).findLikedByUserId(1L);
        verify(movieRepository, times(1)).findRecommendedByGenres(genres, 1L);
        verify(movieMapper, times(1)).moviesToMovieDTOs(List.of(recommendedMovie));
        assertNotNull(result);
        assertTrue(result.contains(movieDTO));
    }
}
