package com.moviebooking.config;

import com.moviebooking.entity.*;
import com.moviebooking.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Preloads sample data (genres, ratings, movies, shows, and an admin user) at application startup.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(GenreRepository genreRepository, RatingRepository ratingRepository,
                      MovieRepository movieRepository, ShowRepository showRepository,
                      UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.genreRepository = genreRepository;
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (movieRepository.count() > 0) {
            log.info("Data already loaded, skipping initialization");
            return;
        }

        log.info("Loading sample data...");

        // Genres
        Genre action = genreRepository.save(new Genre("Action"));
        Genre comedy = genreRepository.save(new Genre("Comedy"));
        Genre drama = genreRepository.save(new Genre("Drama"));
        Genre sciFi = genreRepository.save(new Genre("Sci-Fi"));
        Genre thriller = genreRepository.save(new Genre("Thriller"));

        // Ratings
        Rating u = ratingRepository.save(new Rating("U", "Universal - Suitable for all ages"));
        Rating ua = ratingRepository.save(new Rating("UA", "Parental Guidance - For children under 12"));
        Rating a = ratingRepository.save(new Rating("A", "Adults Only - Restricted to adults"));

        // Movies
        Movie m1 = movieRepository.save(new Movie("Vikram", 174, "Tamil", action, ua));
        Movie m2 = movieRepository.save(new Movie("Jailer", 168, "Tamil", action, ua));
        Movie m3 = movieRepository.save(new Movie("Ponniyin Selvan 2", 160, "Tamil", drama, u));
        Movie m4 = movieRepository.save(new Movie("Leo", 164, "Tamil", thriller, a));
        Movie m5 = movieRepository.save(new Movie("Merry Christmas", 143, "Hindi", thriller, ua));
        Movie m6 = movieRepository.save(new Movie("Interstellar", 169, "English", sciFi, ua));
        Movie m7 = movieRepository.save(new Movie("3 Idiots", 170, "Hindi", comedy, u));

        // Shows (future dates)
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);

        showRepository.save(new Show(m1, tomorrow, "PVR Cinemas - Chennai", 150));
        showRepository.save(new Show(m1, tomorrow.plusHours(4), "PVR Cinemas - Chennai", 150));
        showRepository.save(new Show(m1, tomorrow.plusHours(8), "Sathyam Cinemas - Chennai", 200));

        showRepository.save(new Show(m2, tomorrow, "INOX - Coimbatore", 120));
        showRepository.save(new Show(m2, tomorrow.plusHours(3), "INOX - Coimbatore", 120));

        showRepository.save(new Show(m3, tomorrow.plusHours(1), "Rohini Silver Screens - Chennai", 180));
        showRepository.save(new Show(m3, tomorrow.plusHours(5), "AGS Cinemas - Madurai", 160));

        showRepository.save(new Show(m4, tomorrow.plusHours(2), "Kamala Cinemas - Chennai", 140));
        showRepository.save(new Show(m4, tomorrow.plusHours(6), "Palazzo Cinemas - Trichy", 100));

        showRepository.save(new Show(m5, tomorrow.plusHours(3), "PVR Cinemas - Mumbai", 200));

        showRepository.save(new Show(m6, tomorrow, "IMAX - Bangalore", 250));
        showRepository.save(new Show(m6, tomorrow.plusHours(4), "IMAX - Bangalore", 250));

        showRepository.save(new Show(m7, tomorrow.plusHours(2), "Fun Cinemas - Delhi", 180));

        // Admin user
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(new User("admin", "admin@moviebooking.com",
                    passwordEncoder.encode("admin123"), Role.ADMIN));
        }

        log.info("Sample data loaded successfully: {} genres, {} ratings, {} movies, {} shows",
                genreRepository.count(), ratingRepository.count(),
                movieRepository.count(), showRepository.count());
    }
}
