package dev.peermaute.mealsquare.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Primary
    @Bean
    public void firebaseInit() throws IOException {
        InputStream stream = new ByteArrayInputStream(getFirebaseConfig().getBytes(StandardCharsets.UTF_8));
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(stream)).build();
        FirebaseApp.initializeApp(options);
    }

    private String getFirebaseConfig(){
        return "{\n  " +
                "\"type\": \"service_account\",\n  " +
                "\"project_id\": \"" + System.getenv("FIREBASE_CONFIG_PROJECT_ID") + "\",\n  " +
                "\"private_key_id\": \"" + System.getenv("FIREBASE_CONFIG_PRIVATE_KEY_ID") + "\",\n  " +
                "\"private_key\": \"" + System.getenv("FIREBASE_CONFIG_PRIVATE_KEY") + "\",\n  " +
                "\"client_email\": \"" + System.getenv("FIREBASE_CONFIG_CLIENT_EMAIL") + "\",\n  " +
                "\"client_id\": \"" + System.getenv("FIREBASE_CONFIG_CLIENT_ID") + "\",\n  " +
                "\"auth_uri\": \"" + "https://accounts.google.com/o/oauth2/auth" + "\",\n  " +
                "\"token_uri\": \"" + "https://oauth2.googleapis.com/token" + "\",\n  " +
                "\"auth_provider_x509_cert_url\": \"" + "https://www.googleapis.com/oauth2/v1/certs" + "\",\n  " +
                "\"client_x509_cert_url\": \"" + System.getenv("FIREBASE_CONFIG_CLIENT_X509_CERT_URL") + "\"\n" +
                "}";
    }

    private JSONObject initiateFirebaseConfigJson() {
        JSONObject firebaseConfig = new JSONObject();
        firebaseConfig.put("type", "service_account");
        firebaseConfig.put("project_id", System.getenv("FIREBASE_CONFIG_PROJECT_ID"));
        firebaseConfig.put("private_key_id", System.getenv("FIREBASE_CONFIG_PRIVATE_KEY_ID"));
        firebaseConfig.put("private_key", System.getenv("FIREBASE_CONFIG_PRIVATE_KEY"));
        firebaseConfig.put("client_email", System.getenv("FIREBASE_CONFIG_CLIENT_EMAIL"));
        firebaseConfig.put("client_id", System.getenv("FIREBASE_CONFIG_CLIENT_ID"));
        firebaseConfig.put("auth_uri", "https://accounts.google.com/o/oauth2/auth");
        firebaseConfig.put("token_uri", "https://oauth2.googleapis.com/token");
        firebaseConfig.put("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs");
        firebaseConfig.put("client_x509_cert_url", System.getenv("FIREBASE_CONFIG_CLIENT_X509_CERT_URL"));
        return firebaseConfig;
    }
}
