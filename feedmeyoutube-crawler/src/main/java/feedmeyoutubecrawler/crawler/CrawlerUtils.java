/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecrawler.crawler;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import java.io.IOException;

/**
 * Utility class for the crawler operations. This class has several methodes
 * which will be used in diffrent crawler classes.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class CrawlerUtils {

    /**
     * Gets the {@link Channel} reference for the own channel.
     * @param connection {@link YouTubeConnection} instance which stands for the
     * authorized youtube connection
     * @return {@list Channel} instance which can be used for creating requests
     * to the own channel
     * @throws IOException Can't connect to youtub or can't executing request.
     * @since 1.0
     */
    public static Channel getMyChannel(final YouTubeConnection connection) throws
            IOException {
        final YouTube.Channels.List channelRequest = connection.getYouTube()
                .channels().list("contentDetails");
        channelRequest.setMine(Boolean.TRUE);
        channelRequest.setFields("items/contentDetails, items/id");
        channelRequest.setMaxResults(1L);
        final ChannelListResponse response = channelRequest.execute();

        return response.getItems().get(0);
    }
}
