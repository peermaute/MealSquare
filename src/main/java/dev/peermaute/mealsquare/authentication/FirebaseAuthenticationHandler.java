package dev.peermaute.mealsquare.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import dev.peermaute.mealsquare.util.HttpRequests;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.http.HttpResponse;

@Service
public class FirebaseAuthenticationHandler implements AuthenticationHandler{

    /**
     * Util class to create http requests.
     */
    private HttpRequests httpRequests;

    @Autowired
    public FirebaseAuthenticationHandler(HttpRequests httpRequests){
        this.httpRequests = httpRequests;
    }

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

    public String getIdTokenTestUser(){
        String firebaseKey = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_KEY");
        String email = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_TESTUSER_MAIL");
        String password = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_TESTUSER_PASSWORD");
        return getIdToken(firebaseKey, email, password);
    }

    public String getIdTokenAdminUser(){
        String firebaseKey = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_KEY");
        String email = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_ADMIN_MAIL");
        String password = System.getenv("FIREBASE_IDENTITY_TOOLKIT_SQUARE_APPLICATIONS_ADMIN_PASSWORD");
        return getIdToken(firebaseKey, email, password);
    }

    private String getIdToken(String firebaseKey, String email, String password){
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + firebaseKey;
        JSONObject body = new JSONObject();
        body.put("returnSecureToken", true);
        body.put("email", email);
        body.put("password", password);
        JSONObject responseJson = new JSONObject(httpRequests.postRequest(url, body).body().toString());
        if(!responseJson.has("idToken")){
            throw new IdTokenRetrievalFailedException("Response did not include idToken. Response: " + responseJson.toString());
        }
        return responseJson.getString("idToken");
    }
}
