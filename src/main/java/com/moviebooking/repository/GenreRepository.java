package com.moviebooking.repository;

import com.moviebooking.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Genre} entity operations.
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
