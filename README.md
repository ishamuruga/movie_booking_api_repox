# Movie Ticket Booking API

Production-quality Spring Boot REST API for movie ticket booking with JWT authentication.

## Tech Stack
- Java 17, Spring Boot 3.2.5, Spring Data JPA, Spring Security + JWT
- H2 Database (file-based), Maven

## Quick Start

```bash
# Build and run
mvn clean install
mvn spring-boot:run
```

The app starts on **http://localhost:8080**. H2 console available at **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:file:./data/moviebookingdb`).

## Pre-loaded Data
- **7 movies** (Tamil, Hindi, English) with genres and ratings
- **13 shows** across various theaters
- **Admin user**: username=`admin`, password=`admin123`

## API Reference

### Authentication (public)

```bash
# Signup
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"password123"}'

# Login (returns JWT token)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password123"}'
```

### Movies (requires JWT)

```bash
# Set your token
TOKEN="<paste_token_from_login_response>"

# Get all movies (paginated)
curl http://localhost:8080/api/movies/current \
  -H "Authorization: Bearer $TOKEN"

# Get shows for a movie
curl http://localhost:8080/api/movies/1/shows \
  -H "Authorization: Bearer $TOKEN"
```

### Bookings (requires JWT)

```bash
# Check seat availability
curl http://localhost:8080/api/shows/1/seats \
  -H "Authorization: Bearer $TOKEN"

# Book tickets
curl -X POST http://localhost:8080/api/bookings \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"showId":1,"numberOfSeats":3}'

# Get booking details
curl http://localhost:8080/api/bookings/1 \
  -H "Authorization: Bearer $TOKEN"

# Get user's bookings
curl http://localhost:8080/api/users/2/bookings \
  -H "Authorization: Bearer $TOKEN"
```

### Pagination
All list endpoints support: `?page=0&size=10&sort=id,asc`

## Project Structure

```
src/main/java/com/moviebooking/
├── config/          # Security config, data loader
├── controller/      # REST controllers
├── dto/request/     # Request DTOs with validation
├── dto/response/    # Response DTOs
├── entity/          # JPA entities
├── exception/       # Custom exceptions + global handler
├── filter/          # JWT authentication filter
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic
└── util/            # JWT utility
```
