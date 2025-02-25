package com.dearcoding.recommendationsystem.specification;

import com.dearcoding.recommendationsystem.entity.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class MovieSpecification {

    public static Specification<Movie> filterByTitle(String title) {
        return (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (title == null || title.isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("title")), title.toLowerCase());
        };
    }

    public static Specification<Movie> filterByGenres(List<String> genres) {
        return (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (genres == null || genres.isEmpty()) {
                return cb.conjunction();
            }
            Predicate allGenresMatch = cb.conjunction();
            for (String genre : genres) {
                Predicate genrePredicate = cb.like(cb.lower(root.get("genres")), "%" + genre.toLowerCase() + "%");
                allGenresMatch = cb.and(allGenresMatch, genrePredicate);
            }
            return allGenresMatch;
        };
    }

    public static Specification<Movie> filterByKeywords(List<String> keywords) {
        return (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (keywords == null || keywords.isEmpty()) {
                return cb.conjunction();
            }
            Predicate allKeywordsMatch = cb.conjunction();
            for (String keyword : keywords) {
                Predicate keywordPredicate = cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
                allKeywordsMatch = cb.and(allKeywordsMatch, keywordPredicate);
            }
            return allKeywordsMatch;
        };
    }

    public static Specification<Movie> buildMovieSpecification(String title, List<String> genres, List<String> keywords) {
        return Specification
                .where(filterByTitle(title))
                .and(filterByGenres(genres))
                .and(filterByKeywords(keywords));
    }
}
