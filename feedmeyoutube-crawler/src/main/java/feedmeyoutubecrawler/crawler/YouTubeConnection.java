/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecrawler.crawler;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import feedmeyoutubecrawler.Auth;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Abstraction for connection to youtube. This class manages the initial
 * authorisation and creates the {@link YouTube} which is needed for all
 * requests. It is needed by all crawler classes which want to request
 * information from youtube
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class YouTubeConnection {

    private final Credential _credential;
    private final YouTube _youTube;

    private final static List<String> scopes = Arrays.asList(
            "https://www.googleapis.com/auth/youtube.readonly");
    private static final String APP_NAME = "feedmeyoutube";

    /**
     * Private constructor. Performs the authorisation at youtube.
     *
     * @since 1.0
     */
    public YouTubeConnection() throws IOException {
        _credential = Auth.authorize(scopes, APP_NAME);
        _youTube = new YouTube.Builder(Auth.getHttpTransport(),
                Auth.getJsonFactory(), _credential).setApplicationName(APP_NAME)
                .build();
    }

    /**
     * Gets the {@link YouTube} instance for creating Requests.
     *
     * @return {@link YouTube} instance
     * @since 1.0
     */
    public YouTube getYouTube() {
        return _youTube;
    }
}
