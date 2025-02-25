package com.dearcoding.recommendationsystem.service.impl;

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
import com.dearcoding.recommendationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RatingMapper ratingMapper;
    private final ViewMapper viewMapper;
    private final UserMapper userMapper;
    private final MovieMapper movieMapper;

    @Override
    public HistoryDTO getHistory(Long userId, HistoryReturnType returnType) {
        List<Rating> ratings;
        List<RatingItemDTO> ratingItems = new ArrayList<>();
        List<ViewItemDTO> viewItems = new ArrayList<>();

        switch (returnType) {
            case VIEWS:
                ratings = ratingRepository.findByUser_IdAndViewPercentageIsNotNull(userId);
                viewItems = ratings.stream()
                        .map(viewMapper::ratingToViewItemDTO)
                        .collect(Collectors.toList());
                break;
            case RATINGS:
                ratings = ratingRepository.findByUser_IdAndRatingIsNotNull(userId);
                ratingItems = ratings.stream()
                        .map(ratingMapper::ratingToRatingItemDTO)
                        .collect(Collectors.toList());
                break;
            case ALL:
            default:
                ratings = ratingRepository.findByUser_Id(userId);
                ratingItems = ratings.stream()
                        .filter(rating -> rating.getRating() != null)
                        .map(ratingMapper::ratingToRatingItemDTO)
                        .collect(Collectors.toList());
                viewItems = ratings.stream()
                        .filter(rating -> rating.getViewPercentage() != null)
                        .map(viewMapper::ratingToViewItemDTO)
                        .collect(Collectors.toList());
                break;
        }

        return HistoryDTO.builder()
                .ratings(ratingItems)
                .views(viewItems)
                .build();
    }

    @Override
    public UserDTO addEvent(Long userId, AddUserEventDTO addUserEventDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Movie movie = movieRepository.findById(addUserEventDTO.getMovieId()).orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Optional<Rating> ratingOptional = ratingRepository.findByUser_IdAndMovie_Id(userId, movie.getId());

        Rating rating;
        if(ratingOptional.isPresent()){
            rating = ratingOptional.get();
            if(addUserEventDTO.getViewPercentage() != null) {
                rating.setViewPercentage(addUserEventDTO.getViewPercentage());
            }
            if(addUserEventDTO.getRating() != null) {
                rating.setRating(addUserEventDTO.getRating());
            }
        } else {
            rating = Rating.builder()
                    .id(RatingId.builder()
                            .userId(userId)
                            .movieId(movie.getId())
                            .build())
                    .user(user)
                    .movie(movie)
                    .viewPercentage(addUserEventDTO.getViewPercentage())
                    .rating(addUserEventDTO.getRating())
                    .build();
        }
        ratingRepository.save(rating);

        return userMapper.userToUserDTO(user, getHistory(userId, HistoryReturnType.ALL));
    }


    @Override
    public List<MovieDTO> getRecommendations(Long userId){
        List<Movie> movies = movieRepository.findLikedByUserId(userId);

        List<String> genres = movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .distinct()
                .toList();

        List<Movie> recommendedMovies = movieRepository.findRecommendedByGenres(genres, userId);
        return movieMapper.moviesToMovieDTOs(recommendedMovies);
    }

}
