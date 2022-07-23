package dev.peermaute.mealsquare.read.file;

public class BadFormatException extends RuntimeException{
    public BadFormatException(String errorMessage) {
        super(errorMessage);
    }
}
