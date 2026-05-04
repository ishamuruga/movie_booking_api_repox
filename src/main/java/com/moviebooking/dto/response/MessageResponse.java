package com.moviebooking.dto.response;

/**
 * Generic message response DTO.
 */
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}
