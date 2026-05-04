package com.moviebooking.service;

import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.dto.response.MovieViewershipReport;
import com.moviebooking.dto.response.ShowResponse;
import com.moviebooking.entity.Movie;
import com.moviebooking.entity.Show;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.BookingRepository;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.ShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service for movie and show-related operations.
 */
@Service
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

    public MovieService(MovieRepository movieRepository, ShowRepository showRepository, BookingRepository bookingRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieves all currently available movies with pagination.
     *
     * @param pageable pagination parameters
     * @return a page of movie responses
     */
    public Page<MovieResponse> getCurrentMovies(Pageable pageable) {
        log.debug("Fetching current movies, page: {}", pageable.getPageNumber());
        return movieRepository.findAll(pageable).map(this::toMovieResponse);
    }

    /**
     * Retrieves all shows for a given movie.
     *
     * @param movieId  the movie ID
     * @param pageable pagination parameters
     * @return a page of show responses
     * @throws ResourceNotFoundException if the movie is not found
     */
    public Page<ShowResponse> getShowsByMovie(Long movieId, Pageable pageable) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
        log.debug("Fetching shows for movie id: {}", movieId);
        return showRepository.findByMovieId(movieId, pageable).map(this::toShowResponse);
    }

    /**
     * Retrieves the viewership report for a given movie.
     *
     * @param movieId the movie ID
     * @return the movie viewership report
     * @throws ResourceNotFoundException if the movie is not found
     */
    public MovieViewershipReport getMovieViewership(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        log.debug("Fetching viewership for movie id: {}", movieId);
        Long count = bookingRepository.getTotalViewershipByMovieId(movieId);
        if (count == null) {
            count = 0L;
        }
        return new MovieViewershipReport(movie.getId(), movie.getName(), count);
    }

    private MovieResponse toMovieResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(), movie.getName(), movie.getDuration(), movie.getLanguage(),
                movie.getGenre().getName(), movie.getRating().getCode(), movie.getRating().getDescription()
        );
    }

    private ShowResponse toShowResponse(Show show) {
        return new ShowResponse(
                show.getId(), show.getMovie().getName(), show.getShowTime(),
                show.getTheaterName(), show.getTotalSeats(), show.getAvailableSeats()
        );
    }
}
