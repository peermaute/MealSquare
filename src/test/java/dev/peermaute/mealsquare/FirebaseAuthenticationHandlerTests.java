package dev.peermaute.mealsquare;

import dev.peermaute.mealsquare.authentication.FirebaseAuthenticationHandler;
import dev.peermaute.mealsquare.authentication.IdTokenRetrievalFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class FirebaseAuthenticationHandlerTests {

    @Autowired
    FirebaseAuthenticationHandler firebaseAuthenticationHandler;

    @Test
    void testIdTokenAdmin(){
        try{
            firebaseAuthenticationHandler.getIdTokenAdminUser();
        }
        catch (IdTokenRetrievalFailedException e){
            fail();
        }
    }

    @Test
    void testIdTokenTestUser(){
        try{
            firebaseAuthenticationHandler.getIdTokenTestUser();
        }
        catch (IdTokenRetrievalFailedException e){
            fail();
        }
    }
}
