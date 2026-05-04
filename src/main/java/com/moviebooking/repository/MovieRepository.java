package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Movie} entity operations.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
