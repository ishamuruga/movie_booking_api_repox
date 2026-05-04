package com.moviebooking.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for show/screening details in API responses.
 */
public class ShowResponse {

    private Long id;
    private String movieName;
    private LocalDateTime showTime;
    private String theaterName;
    private Integer totalSeats;
    private Integer availableSeats;

    public ShowResponse(Long id, String movieName, LocalDateTime showTime,
                        String theaterName, Integer totalSeats, Integer availableSeats) {
        this.id = id;
        this.movieName = movieName;
        this.showTime = showTime;
        this.theaterName = theaterName;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public Long getId() { return id; }
    public String getMovieName() { return movieName; }
    public LocalDateTime getShowTime() { return showTime; }
    public String getTheaterName() { return theaterName; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getAvailableSeats() { return availableSeats; }
}
