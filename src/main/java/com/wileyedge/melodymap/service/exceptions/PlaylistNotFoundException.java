package com.wileyedge.melodymap.service.exceptions;


public class PlaylistNotFoundException extends Exception {
    public PlaylistNotFoundException(String message) {
        super(message);
    }

    public PlaylistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
