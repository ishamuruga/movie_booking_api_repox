package com.moviebooking.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for booking details in API responses.
 */
public class BookingResponse {

    private Long id;
    private String username;
    private String movieName;
    private String theaterName;
    private LocalDateTime showTime;
    private Integer numberOfSeats;
    private LocalDateTime bookedAt;

    public BookingResponse(Long id, String username, String movieName, String theaterName,
                           LocalDateTime showTime, Integer numberOfSeats, LocalDateTime bookedAt) {
        this.id = id;
        this.username = username;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.showTime = showTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedAt = bookedAt;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getMovieName() { return movieName; }
    public String getTheaterName() { return theaterName; }
    public LocalDateTime getShowTime() { return showTime; }
    public Integer getNumberOfSeats() { return numberOfSeats; }
    public LocalDateTime getBookedAt() { return bookedAt; }
}
