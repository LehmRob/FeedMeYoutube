/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecrawler.crawler;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Crawl all youtube videos for the configured channel. Crawling all classes
 * with the desired video ids.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class PlaylistCrawler implements Crawler<Playlist> {
    private final YouTubeConnection _connection;
    private final Channel _channel;
    private final YouTube.Playlists.List _playlistReq;
    private String _nextToken = "";

    public static final Logger LOG = LoggerFactory.getLogger(PlaylistCrawler.class);

    /**
     * Constructor. Initialize the playlist crawler.
     *
     * @param connection instance for {@link YouTubeConnection}.
     * @throws IOException Error while requesting the own {@link Channel}
     *                     refence
     * @since 1.0
     */
    public PlaylistCrawler(final YouTubeConnection connection) throws
        IOException {
        _connection = connection;
        _channel = CrawlerUtils.getMyChannel(connection);
        _playlistReq = createPlaylistReq();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {return _nextToken != null;}

    /** {@inheritDoc} */
    @Override
    public List<Playlist> getNext() throws IOException {
        final List <Playlist> playlists = new ArrayList<>();

        _playlistReq.setPageToken(_nextToken);
        PlaylistListResponse playlistListResponse = _playlistReq.execute();
        playlists.addAll(playlistListResponse.getItems());
        _nextToken = playlistListResponse.getNextPageToken();

        return playlists;
    }

    /**
     * Creates the playlist request.
     *
     * This methode creates the request for getting the playlists.
     *
     * @return Created {@link Playlist} Request.
     *
     * @since 1.0
     */
    private YouTube.Playlists.List createPlaylistReq() throws IOException {
    	final YouTube.Playlists.List request = _connection.getYouTube().playlists().list(
                "id,snippet");
        request.setMaxResults(1L);
        request.setChannelId(_channel.getId());
        request.setFields("items(id,snippet/title,snippet/description)," +
                "nextPageToken,pageInfo");
        return request;
    }
}
