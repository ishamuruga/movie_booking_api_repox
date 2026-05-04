package com.moviebooking.repository;

import com.moviebooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for {@link Booking} entity operations.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT SUM(b.numberOfSeats) FROM Booking b WHERE b.show.movie.id = :movieId")
    Long getTotalViewershipByMovieId(@Param("movieId") Long movieId);
}
