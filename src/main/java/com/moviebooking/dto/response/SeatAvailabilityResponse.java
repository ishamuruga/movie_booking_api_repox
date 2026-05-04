package com.moviebooking.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for seat availability information.
 */
public class SeatAvailabilityResponse {

    private Long showId;
    private String movieName;
    private String theaterName;
    private LocalDateTime showTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer bookedSeats;

    public SeatAvailabilityResponse(Long showId, String movieName, String theaterName,
                                     LocalDateTime showTime, Integer totalSeats,
                                     Integer availableSeats) {
        this.showId = showId;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.showTime = showTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.bookedSeats = totalSeats - availableSeats;
    }

    public Long getShowId() { return showId; }
    public String getMovieName() { return movieName; }
    public String getTheaterName() { return theaterName; }
    public LocalDateTime getShowTime() { return showTime; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getAvailableSeats() { return availableSeats; }
    public Integer getBookedSeats() { return bookedSeats; }
}
