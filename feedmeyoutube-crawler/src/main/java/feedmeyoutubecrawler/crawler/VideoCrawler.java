/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecrawler.crawler;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import feedmeyoutubecore.youtube.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Crawler for all youtube videos. This class is responsible for crawling all
 * youtube videos from the configured channel.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class VideoCrawler implements Crawler<Video> {
    private YouTubeConnection _connection;
    private Channel _myChannel;
    private final YouTube.PlaylistItems.List _videosRequest;
    private String _nextToken = "";

    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

    /**
     * Constructor.
     *
     * @param connection instance of {@link YouTubeConnection}
     * @throws IOException Error occured during the authorisation
     * @since 1.0
     */
    public VideoCrawler(final YouTubeConnection connection) throws IOException {
        init(connection);
        _videosRequest = createPlaylistItemsRequest(_myChannel.
                getContentDetails().getRelatedPlaylists().getUploads());
    }

    /**
     * Constructor for a custom playlist.
     *
     * @param connection {@link YouTubeConnection} instance which stands for
     * the connection to the youtube servers<
     *
     * @since 1.0
     */
    public VideoCrawler(final YouTubeConnection connection, final String playlistId)
        throws IOException {
        init(connection);
        _videosRequest = createPlaylistItemsRequest(playlistId);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {
        return _nextToken != null;
    }

    /** {@inheritDoc} */
    @Override
    public List<Video> getNext() throws IOException {
        final List<PlaylistItem> playlistItems = new ArrayList<>();

        _videosRequest.setPageToken(_nextToken);
        PlaylistItemListResponse playlistResponse
                = _videosRequest.execute();
        playlistItems.addAll(playlistResponse.getItems());
        _nextToken = playlistResponse.getNextPageToken();

        return mapVideos(playlistItems);
    }

    /**
     * Init the local attributes
     *
     * @param connection Abstranction for the connection to the youtube servers
     *
     * @since 1.0
     */
    private void init(final YouTubeConnection connection) throws IOException {
        _connection = connection;
        _myChannel = CrawlerUtils.getMyChannel(_connection);
        LOG.debug("My channel {}", _myChannel.toPrettyString());
    }

    /**
     * Map an {@link List} of {@link PlaylistItem}s to an {@link List} of
     * {@link Video}s
     *
     * @param playlistItems {@link List} of {@link PlaylistItem}s which will be
     *                      mapped
     * @return {@link List} of mapped {@link Video}
     *
     * @since 1.0
     */
    private static List<Video> mapVideos(
            final List<PlaylistItem> playlistItems) {
        final List<Video> mappedVids = new ArrayList<>();

        playlistItems.stream().forEach((item) -> mappedVids.add(mapVideo(item)));

        return mappedVids;
    }

    /**
     * Map an {@link PlaylistItem} to an {@link Video}
     *
     * @param item {@link PlaylistItem} which will be mapped
     * @return {@link Video} which will be the result of the mapping
     *
     * @since 1.0
     */
    private static Video mapVideo(final PlaylistItem item) {
        return new Video(item.getContentDetails().getVideoId(), item.
                getSnippet().getTitle(), item.getSnippet().getDescription(),
                null);
    }

    /**
     * Creates an request to get {@link PlaylistItem}s for the playlist id. If
     * the playlist id is the id of the uploaded videos than the methode creates
     * an request to get all uploaded videos.
     *
     * @param playlistId {@link String} with the id of the correspondending
     *                   playlist.
     * @return {@link PlaylistItem} request to get all elements of the playlist.
     *
     * @throws IOException An error occurs while creating the request.
     *
     * @since 1.0
     */
    private YouTube.PlaylistItems.List createPlaylistItemsRequest(
            final String playlistId) throws IOException {
        final YouTube.PlaylistItems.List request = _connection.getYouTube()
                .playlistItems().list("id,contentDetails,snippet");
        request.setMaxResults(5L);
        request.setPlaylistId(playlistId);
        request.setFields("items(contentDetails/videoId,snippet/title, "
                + "snippet/description, snippet/publishedAt),"
                + "nextPageToken, pageInfo");
        return request;
    }
}
