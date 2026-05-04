package com.moviebooking.repository;

import com.moviebooking.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Rating} entity operations.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
