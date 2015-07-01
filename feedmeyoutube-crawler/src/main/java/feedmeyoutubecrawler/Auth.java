package feedmeyoutubecrawler;

/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import feedmeyoutubecore.AppConfig;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Performing the authentification on youtube
 *
 * @author Robert Lehmann
 *
 * @since 1.0
 */
public class Auth {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";
    private static final AppConfig config = ConfigFactory.
            create(AppConfig.class);
    private static final Pattern HOME_DIR = Pattern.compile("~");
    private static final Logger LOG = LoggerFactory.getLogger(Auth.class);

    /**
     * Perform the authorisation for the youtube account
     *
     * @param scopes              {@linkplain List} of scopes to perform
     *                            authorization
     * @param credentailDataStore name of the credential datastore
     *
     * @return {@linkplain Credential} object which is used for Requests
     *
     * @throws IOException an error occurs during the authorisation.
     *
     * @since 1.0
     */
    public static Credential authorize(List<String> scopes,
            String credentailDataStore)
            throws IOException {
        final Reader reader = new InputStreamReader(Auth.class.
                getResourceAsStream("/youtube.json"));
        final GoogleClientSecrets secrets = GoogleClientSecrets.load(
                JSON_FACTORY, reader);
        final FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(
                Paths.get(System
                        .getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY).
                toFile());
        final DataStore<StoredCredential> dataStore = dataStoreFactory.
                getDataStore(credentailDataStore);
        final GoogleAuthorizationCodeFlow flow
                = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                        JSON_FACTORY,
                        secrets, scopes).setCredentialDataStore(dataStore).
                build();
        final LocalServerReceiver receiver = new LocalServerReceiver.Builder().
                setPort(8080).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(
                config.userId());
    }

    /**
     * Gets the instance of {@linkplain HttpTransport}
     *
     * @return instance of {@linkplain HttpTransport}
     *
     * @since 1.0
     */
    public static HttpTransport getHttpTransport() {
        return HTTP_TRANSPORT;
    }

    /**
     * Gets the instance of {@linkplain JsonFactory}
     *
     * @return instance of {@linkplain JsonFactory}
     *
     * @since 1.0
     */
    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
