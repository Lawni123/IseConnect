package com.iseConnect.certificate.NPTEL;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveUtils {
    private static final String APPLICATION_NAME = "ISE Connect";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    private static final String[] CREDENTIALS_FILES = {
        "client_secret.json",
        "service-account-key.json",
        "google-credentials.json"
    };

    public static Drive getDriveService(ServletContext context) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        try (InputStream in = getCredentialsStream(context)) {
            if (in == null) {
                throw new IOException("Google service account file not found. Tried:\n" +
                    String.join("\n- /WEB-INF/", CREDENTIALS_FILES));
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                    .createScoped(SCOPES);

            return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
    }

    private static InputStream getCredentialsStream(ServletContext context) {
        for (String filename : CREDENTIALS_FILES) {
            InputStream in = context.getResourceAsStream("/WEB-INF/" + filename);
            if (in != null) {
                return in;
            }
        }
        return null;
    }
}
