package com.wellbeignatwork.backend.exceptions;

public class InvalidGameException extends Exception {
    private String message;
    public InvalidGameException(String message){ this.message=message;}
    public String getmessage(){return message;}





}
