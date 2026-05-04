package com.moviebooking.controller;

import com.moviebooking.dto.request.BookingRequest;
import com.moviebooking.dto.response.BookingResponse;
import com.moviebooking.dto.response.SeatAvailabilityResponse;
import com.moviebooking.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for booking and seat availability endpoints.
 */
@RestController
@RequestMapping("/api")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Returns seat availability for a given show.
     *
     * @param showId the show ID
     * @return seat availability details
     */
    @GetMapping("/shows/{showId}/seats")
    public ResponseEntity<SeatAvailabilityResponse> getSeatAvailability(@PathVariable Long showId) {
        log.info("GET /api/shows/{}/seats", showId);
        return ResponseEntity.ok(bookingService.getSeatAvailability(showId));
    }

    /**
     * Books tickets for a show. The user is derived from the JWT token.
     *
     * @param request     the booking request
     * @param userDetails the authenticated user
     * @return the booking confirmation
     */
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> bookTickets(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /api/bookings by user: {}", userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.bookTickets(request, userDetails.getUsername()));
    }

    /**
     * Retrieves details of a specific booking.
     *
     * @param bookingId the booking ID
     * @return the booking details
     */
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        log.info("GET /api/bookings/{}", bookingId);
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    /**
     * Retrieves all bookings for a given user.
     *
     * @param userId   the user ID
     * @param pageable pagination parameters
     * @return paginated list of bookings
     */
    @GetMapping("/users/{userId}/bookings")
    public ResponseEntity<Page<BookingResponse>> getUserBookings(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("GET /api/users/{}/bookings", userId);
        return ResponseEntity.ok(bookingService.getUserBookings(userId, pageable));
    }
}
