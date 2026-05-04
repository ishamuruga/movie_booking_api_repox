package com.moviebooking.dto.response;

/**
 * DTO for movie viewership report.
 */
public record MovieViewershipReport(
        Long movieId,
        String movieTitle,
        Long viewershipCount
) {}