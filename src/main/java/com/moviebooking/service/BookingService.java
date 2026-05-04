package com.moviebooking.service;

import com.moviebooking.dto.request.BookingRequest;
import com.moviebooking.dto.response.BookingResponse;
import com.moviebooking.dto.response.SeatAvailabilityResponse;
import com.moviebooking.entity.Booking;
import com.moviebooking.entity.Show;
import com.moviebooking.entity.User;
import com.moviebooking.exception.BadRequestException;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.BookingRepository;
import com.moviebooking.repository.ShowRepository;
import com.moviebooking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service handling ticket booking operations.
 */
@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns seat availability information for a given show.
     *
     * @param showId the show ID
     * @return seat availability details
     * @throws ResourceNotFoundException if the show is not found
     */
    public SeatAvailabilityResponse getSeatAvailability(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));
        log.debug("Seat availability for show {}: {}/{}", showId, show.getAvailableSeats(), show.getTotalSeats());

        return new SeatAvailabilityResponse(
                show.getId(), show.getMovie().getName(), show.getTheaterName(),
                show.getShowTime(), show.getTotalSeats(), show.getAvailableSeats()
        );
    }

    /**
     * Books tickets for a show. Validates seat availability and reduces available seats atomically.
     *
     * @param request  the booking request
     * @param username the authenticated user's username
     * @return the booking confirmation details
     * @throws ResourceNotFoundException if the show or user is not found
     * @throws BadRequestException       if not enough seats are available
     */
    @Transactional
    public BookingResponse bookTickets(BookingRequest request, String username) {
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + request.getShowId()));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (show.getAvailableSeats() < request.getNumberOfSeats()) {
            throw new BadRequestException(
                    "Not enough seats available. Requested: " + request.getNumberOfSeats()
                            + ", Available: " + show.getAvailableSeats());
        }

        show.setAvailableSeats(show.getAvailableSeats() - request.getNumberOfSeats());
        showRepository.save(show);

        Booking booking = new Booking(user, show, request.getNumberOfSeats());
        booking = bookingRepository.save(booking);

        log.info("Booking created: id={}, user={}, show={}, seats={}",
                booking.getId(), username, show.getId(), request.getNumberOfSeats());

        return toBookingResponse(booking);
    }

    /**
     * Retrieves booking details by booking ID.
     *
     * @param bookingId the booking ID
     * @return the booking details
     * @throws ResourceNotFoundException if the booking is not found
     */
    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        return toBookingResponse(booking);
    }

    /**
     * Retrieves all bookings for a given user with pagination.
     *
     * @param userId   the user ID
     * @param pageable pagination parameters
     * @return a page of booking responses
     * @throws ResourceNotFoundException if the user is not found
     */
    public Page<BookingResponse> getUserBookings(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        log.debug("Fetching bookings for user id: {}", userId);
        return bookingRepository.findByUserId(userId, pageable).map(this::toBookingResponse);
    }

    private BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(), booking.getUser().getUsername(),
                booking.getShow().getMovie().getName(), booking.getShow().getTheaterName(),
                booking.getShow().getShowTime(), booking.getNumberOfSeats(), booking.getBookedAt()
        );
    }
}
