package dev.peermaute.mealsquare.authentication;

public class IdTokenRetrievalFailedException extends RuntimeException{
    public IdTokenRetrievalFailedException(String errorMessage) {
        super(errorMessage);
    }
}
