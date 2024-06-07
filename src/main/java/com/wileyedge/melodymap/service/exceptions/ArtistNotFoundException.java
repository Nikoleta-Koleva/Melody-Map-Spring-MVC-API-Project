package com.wileyedge.melodymap.service.exceptions;

public class ArtistNotFoundException extends Exception {
    public ArtistNotFoundException(String message) {
        super(message);
    }

    public ArtistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
