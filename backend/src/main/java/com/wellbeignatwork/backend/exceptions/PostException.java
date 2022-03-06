package com.wellbeignatwork.backend.exceptions;

public class PostException extends RuntimeException {
    public PostException(final String postNotFound) {
        super(postNotFound);
    }
}
