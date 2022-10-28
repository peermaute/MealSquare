package dev.peermaute.mealsquare.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FirebaseAuthenticationHandler implements AuthenticationHandler{
    public String getBearerToken(String fullToken) {
        String bearerToken = null;
        if (StringUtils.hasText(fullToken) && fullToken.startsWith("Bearer ")) {
            bearerToken = fullToken.substring(7);
        }
        return bearerToken;
    }

    @Override
    public boolean verifyToken(String bearerToken){
        try{
            FirebaseAuth.getInstance().verifyIdToken(bearerToken);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getUid(String bearerToken) {
        try{
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(bearerToken);
            return token.getUid();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Uid retrieval failed");
        }
    }
}
