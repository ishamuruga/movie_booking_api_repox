package com.moviebooking.dto.response;

/**
 * DTO for movie details in API responses.
 */
public class MovieResponse {

    private Long id;
    private String name;
    private Integer duration;
    private String language;
    private String genre;
    private String ratingCode;
    private String ratingDescription;

    public MovieResponse(Long id, String name, Integer duration, String language,
                         String genre, String ratingCode, String ratingDescription) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.language = language;
        this.genre = genre;
        this.ratingCode = ratingCode;
        this.ratingDescription = ratingDescription;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getDuration() { return duration; }
    public String getLanguage() { return language; }
    public String getGenre() { return genre; }
    public String getRatingCode() { return ratingCode; }
    public String getRatingDescription() { return ratingDescription; }
}
