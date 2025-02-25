package com.dearcoding.recommendationsystem.repository;

import com.dearcoding.recommendationsystem.entity.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @Query(value = "SELECT m.* " +
            "FROM movie m " +
            "LEFT JOIN rating r ON r.movie_id = m.id " +
            "WHERE (:genre IS NULL OR LOWER(m.genres) LIKE LOWER(CONCAT('%', :genre, '%'))) " +
            "GROUP BY m.id " +
            "HAVING (:minRating IS NULL OR AVG(CASE " +
            "       WHEN r.rating IS NOT NULL THEN r.rating " +
            "       WHEN r.view_percentage BETWEEN 60 AND 80 THEN 4 " +
            "       WHEN r.view_percentage BETWEEN 80 AND 100 THEN 5 " +
            "       ELSE NULL END) >= :minRating) " +
            "AND (:maxRating IS NULL OR AVG(CASE " +
            "       WHEN r.rating IS NOT NULL THEN r.rating " +
            "       WHEN r.view_percentage BETWEEN 60 AND 80 THEN 4 " +
            "       WHEN r.view_percentage BETWEEN 80 AND 100 THEN 5 " +
            "       ELSE NULL END) <= :maxRating) " +
            "ORDER BY m.id ASC",
            nativeQuery = true)
    List<Movie> findMoviesWithFilters(@Param("genre") String genre,
                                      @Param("minRating") Double minRating,
                                      @Param("maxRating") Double maxRating);


    @Query(value = "SELECT DISTINCT m.* " +
            "FROM movie m " +
            "JOIN rating r ON r.movie_id = m.id " +
            "WHERE r.user_id = :userId " +
            "AND ((r.rating IS NOT NULL AND r.rating >= 4) " +
            "     OR (r.rating IS NULL AND r.view_percentage >= 60)) " +
            "ORDER BY m.id ASC",
            nativeQuery = true)
    List<Movie> findLikedByUserId(@Param("userId") Long userId);


    @Query(value = "SELECT m.*, COUNT(r.rating) AS rating_count " +
            "FROM movie m " +
            "LEFT JOIN rating r ON r.movie_id = m.id " +
            "WHERE (:genres IS NULL OR EXISTS (" +
            "       SELECT 1 FROM UNNEST(STRING_TO_ARRAY(m.genres, '|')) g " +
            "       WHERE g IN (:genres)" +
            "   )) " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM rating r2 " +
            "   WHERE r2.movie_id = m.id AND r2.user_id = :userId" +
            ") " +
            "GROUP BY m.id " +
            "ORDER BY rating_count DESC, m.id ASC",
            nativeQuery = true)
    List<Movie> findRecommendedByGenres(@Param("genres") List<String> genres, @Param("userId") Long userId);



}
