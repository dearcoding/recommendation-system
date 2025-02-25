package com.dearcoding.recommendationsystem.entity;

import com.dearcoding.recommendationsystem.entity.composedKey.RatingId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private Integer rating;

    @Column(name = "view_percentage")
    private Integer viewPercentage;


}
