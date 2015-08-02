/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecrawler.crawler;

import com.google.api.services.youtube.model.Channel;
import java.io.IOException;

/**
 * Crawl all youtube videos for the configured channel. Crawling all classes
 * with the desired video ids.
 * 
 * @author Robert Lehmann
 * @since 1.0
 */
public class PlaylistCrawler {
    private final YouTubeConnection _connection;
    private final Channel _channel;
    
    /**
     * Constructor. Initialize the playlist crawler.
     * @param connection instance for {@link YouTubeConnection}.
     * @throws IOException Error while requesting the own {@link Channel}
     * refence
     * @since 1.0
     */
    public PlaylistCrawler(final YouTubeConnection connection) throws 
            IOException {
        _connection = connection;
        _channel = CrawlerUtils.getMyChannel(connection);
    }
}
