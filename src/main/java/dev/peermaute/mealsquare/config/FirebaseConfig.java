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
        JSONObject firebaseConfig = initiateFirebaseConfigJson();
        String fireBaseConfigString = getFirebaseConfig();
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
        firebaseConfig.put("private_key", System.getenv("-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9G4jXyW2vQF0z\n9x9eVRUc3/jRLEaKq0vZ+v6SRdTePIxIsUTgIyWMkGjAYFKSFEleEoxEwez/XXeX\nsqDsUK51RVBZVAoFE55n2gJXO+Z+QsVDqmaFiHXNBEnZcQ9N1rDd6Q3gKmrLbQEN\nsBjnoHzdaW0rHZZMSJeBWWv7WCiUmm5MFUi31B4Q1Q4UWRTDvgsQexYDl9TqBfvf\nmMrVovGNNCH1FXeWJuQ6rXoMQbswGDJRaHAxkDmSX41cPDM8zWKWVDvMIUt9gSVY\nYh1cWMmyoBcaSXiEUqwX7PnQoxOIUpLGPpdvDWRtOSq8z4BmLsbGDDS9V5mA7UPN\no5YH/POTAgMBAAECggEAGhgZD1w/kthTpA6HA7oYzOBePJAaocWA6htiElFUI2BW\neSy2KiQfaSB/cSrkFJJ7tzaMUsExpeEXVgAziA2arRrl5erx66u+hr+vOqtMza0C\npv4gjG99KyTa2IgVylyuu3LjqRl2nWBgA1atG7+/oGu3lnn22crbK7bo7rNGUjl/\nTpz9N/9grSeEZNtO1HBcnxeAF6dlxS+w1namGmD+ywp/pAIjV34tvsldmOCOzGtA\n1Hwek0bGlvMw1xVYcBsWKWVPh/ShBRWeXwmviGPSi1EWE71GhMaINVtKId8fDXGD\nYHqBvVhX6W2VX1JtD9XcONHBDUlwDaNIAkPJbMzJ+QKBgQD0K1r0Qb2OHdOkPpJr\nAfs+nNcv4mSC4xvtKL9kLQGfLWKgRryuRu2kjaMnsPoeGOFBpRtWK1xtujpNjyjL\nf37/OJn9Rd6MY3XCFzC/qxeH3Yv341oFaqcEgUMP17B7fCcBfsgPyKxB2nHO/xnd\nD0BKYMhIbrT1yrL+rMrJYgceGQKBgQDGRTMsyXzKFvFPI4stNeuNTkr9iCjNGe3r\nwhQmKBi6QugvpG+Ei0QHEHZBqpxC0djz+UTnk4+oLuO02YMf8gLzvtwsm2JHQD48\nbfrx/qGd9EkD1LVcw2c0acQoChOePsxiev4nvo18ykTB25mJGXdbUQevlXuWZl+g\n7C3+Ylf8iwKBgGxCtOdMJsHWk7plTAJKNJ4EaJ0AolQuYnl68bF3CxsKwooSEyKq\nrD+j1U2J+LxTh7zVehNw0mCMi8FL0mi4NRTwgcZ8rjpM9y+BnMm/EIXbQ247zfRM\n35Ttw1BI8CUwXRMapZXqAgaI1qFTFqKfWoAf/W2wOGgq4cZt0tQLtOAxAoGBALnW\nFKBO/nMgy+8kXofyuSTKFInMPIn5WsKWN3KINJ0opx44nvn52rpQWLc15Z7Pt+z9\nX/M6oPS/dliFY1zm5pN1+kKpJZWpBmx3nTWDOBKn7GP11EPtNueultf8keTJ8aIA\nrAWumkk0APw/C4qwXuotaenOT3dp3wojNPzxSoz/AoGAe07QNX4YDSCE4lLm3pjQ\nlX36nUiSZCpQMpiDxwGJaLDteZqQsDx0DXEgulP43M+z/QbcLcax0VCykOmFOtIh\n32WVmojV+zwp8etFlaGfUdq1MZlrR4IhlAWGTi05qDiJK+pXsckLmToGLsVsDX+N\ndcMPVhap0dYZ5/SoBTKLqpE=\n-----END PRIVATE KEY-----\n"));
        firebaseConfig.put("client_email", System.getenv("FIREBASE_CONFIG_CLIENT_EMAIL"));
        firebaseConfig.put("client_id", System.getenv("FIREBASE_CONFIG_CLIENT_ID"));
        firebaseConfig.put("auth_uri", "https://accounts.google.com/o/oauth2/auth");
        firebaseConfig.put("token_uri", "https://oauth2.googleapis.com/token");
        firebaseConfig.put("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs");
        firebaseConfig.put("client_x509_cert_url", System.getenv("FIREBASE_CONFIG_CLIENT_X509_CERT_URL"));
        return firebaseConfig;
    }
}
