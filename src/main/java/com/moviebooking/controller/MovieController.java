package com.moviebooking.controller;

import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.dto.response.MovieViewershipReport;
import com.moviebooking.dto.response.ShowResponse;
import com.moviebooking.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for movie and show-related endpoints.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Returns all currently available movies.
     *
     * @param pageable pagination parameters (default page size: 10)
     * @return paginated list of movies
     */
    @GetMapping("/current")
    public ResponseEntity<Page<MovieResponse>> getCurrentMovies(
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("GET /api/movies/current");
        return ResponseEntity.ok(movieService.getCurrentMovies(pageable));
    }

    /**
     * Returns all shows for a given movie.
     *
     * @param movieId  the movie ID
     * @param pageable pagination parameters
     * @return paginated list of shows
     */
    @GetMapping("/{movieId}/shows")
    public ResponseEntity<Page<ShowResponse>> getShowsByMovie(
            @PathVariable Long movieId,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("GET /api/movies/{}/shows", movieId);
        return ResponseEntity.ok(movieService.getShowsByMovie(movieId, pageable));
    }

    /**
     * Returns the viewership report for a given movie.
     *
     * @param movieId the movie ID
     * @return the movie viewership report
     */
    @GetMapping("/{movieId}/viewership")
    public ResponseEntity<MovieViewershipReport> getMovieViewership(@PathVariable Long movieId) {
        log.info("GET /api/movies/{}/viewership", movieId);
        return ResponseEntity.ok(movieService.getMovieViewership(movieId));
    }
}
