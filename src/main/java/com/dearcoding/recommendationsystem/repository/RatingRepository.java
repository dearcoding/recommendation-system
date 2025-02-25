package com.dearcoding.recommendationsystem.repository;

import com.dearcoding.recommendationsystem.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
    List<Rating> findByUser_IdAndRatingIsNotNull(Long userId);

    List<Rating> findByUser_Id(Long userId);

    List<Rating> findByUser_IdAndViewPercentageIsNotNull(Long userId);

    Optional<Rating> findByUser_IdAndMovie_Id(Long userId, Long movieId);
}
