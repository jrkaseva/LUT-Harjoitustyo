package com.example.lutemongame.Game.Areas;

/**
 * Exception class for errors when trying to find a Lutemon with an Id that isn't in that data structure
 */
public class BadIdException extends Exception {

    /**
     * Constructor with message
     * @param message to display
     */
    public BadIdException(String message) {
        super(message);
    }

}
