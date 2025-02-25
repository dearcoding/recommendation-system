package com.dearcoding.recommendationsystem.entity.composedKey;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Embeddable
public class RatingId implements Serializable {
    private Long userId;
    private Long movieId;
}
