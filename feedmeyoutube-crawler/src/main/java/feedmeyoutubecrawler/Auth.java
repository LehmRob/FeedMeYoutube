package feedmeyoutubecrawler;

// The MIT License (MIT)
// Copyright (c) 2015 Robert Lehmann
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
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
import com.google.api.services.youtube.YouTube;
import sun.management.jmxremote.LocalRMIServerSocketFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;

/**
 * Performing the authentification on youtube
 *
 * @author Robert Lehmann
 *
 * @since 1.0
 */
public class Auth
{
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    /**
     * Perform the authorisation for the youtube account
     * @param scopes {@linkplain List} of scopes to perform authorization
     * @param credentailDataStore name of the credential datastore
     * @param jsonPath {@linkplain Path} of the json file which
     *
     * @return {@linkplain Credential} object which is used for Requests
     * @throws IOException an error occurs during the authorisation.
     *
     * @since 1.0
     */
    public static Credential authorize (List<String> scopes, String credentailDataStore, final Path jsonPath)
            throws IOException {
        final Reader reader = new InputStreamReader(Files.newInputStream(jsonPath,
                StandardOpenOption.READ));
        final GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        final FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(Paths.get(System
                .getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY).toFile());
        final DataStore<StoredCredential> dataStore = dataStoreFactory.getDataStore(credentailDataStore);
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                secrets, scopes).setCredentialDataStore(dataStore).build();
        final LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Gets the instance of {@linkplain HttpTransport}
     * @return instance of {@linkplain HttpTransport}
     *
     * @since 1.0
     */
    public static HttpTransport getHttpTransport() {
        return HTTP_TRANSPORT;
    }

    /**
     * Gets the instance of {@linkplain JsonFactory}
     * @return instance of {@linkplain JsonFactory}
     *
     * @since 1.0
     */
    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
