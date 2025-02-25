package com.dearcoding.recommendationsystem.entity;

import com.dearcoding.recommendationsystem.converter.GenreConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Convert(converter = GenreConverter.class)
    @Column(nullable = false)
    private List<String> genres;
}
