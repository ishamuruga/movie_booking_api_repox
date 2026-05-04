package com.moviebooking.repository;

import com.moviebooking.entity.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for {@link Show} entity operations.
 */
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    Page<Show> findByMovieId(Long movieId, Pageable pageable);
}
