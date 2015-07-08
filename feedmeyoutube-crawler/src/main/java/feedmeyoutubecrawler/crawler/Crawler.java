package feedmeyoutubecrawler.crawler;

/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.common.collect.Lists;
import feedmeyoutubecore.obj.YouTubeVideo;
import feedmeyoutubecrawler.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main crawler class which runs the crawler
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class Crawler {

    private final Credential _credential;
    private final YouTube _youtube;
    private final Channel _myChannel;

    private static final String APP_NAME = "feedmeyoutube";
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

    private final static List<String> scopes = Lists.newArrayList(
            "https://www.googleapis.com/auth/youtube.readonly");

    /**
     * Constructor.
     *
     * @throws IOException Error occured during the authorisation
     * @since 1.0
     */
    public Crawler() throws IOException {
        _credential = Auth.authorize(scopes, APP_NAME);
        _youtube = new YouTube.Builder(Auth.getHttpTransport(), Auth.
                getJsonFactory(), _credential).setApplicationName(APP_NAME)
                .build();
        _myChannel = getMyChannel();
        LOG.debug("My channel {}", _myChannel.toPrettyString());
    }

    public List<YouTubeVideo> getUploadedVideos() throws IOException {
        final String uploadPlaylistId = _myChannel.getContentDetails().
                getRelatedPlaylists().getUploads();
        final List<PlaylistItem> playlistItems = new ArrayList<>();
        final YouTube.PlaylistItems.List playlistItemsRequest
                = createPlaylistItemsRequest(uploadPlaylistId);
        String nextToken = "";
        
        do {
            playlistItemsRequest.setPageToken(nextToken);
            PlaylistItemListResponse playlistResponse
                    = playlistItemsRequest.execute();
            for (PlaylistItem item : playlistResponse.getItems())
            {
                LOG.debug(item.toPrettyString());
            }
            playlistItems.addAll(playlistResponse.getItems());
            nextToken = playlistResponse.getNextPageToken();
        } while (nextToken != null);

        return mapVideos(playlistItems);
    }

    /**
     * Map an {@link List} of {@link PlaylistItem}s to an {@link List} of
     * {@link YouTubeVideo}s
     *
     * @param playlistItems {@link List} of {@link PlaylistItem}s which will be
     *                      mapped
     * @return {@link List} of mapped {@link YouTubeVideo}
     *
     * @since 1.0
     */
    private static List<YouTubeVideo> mapVideos(
            final List<PlaylistItem> playlistItems) {
        final List<YouTubeVideo> mappedVids = new ArrayList<>();

        for (final PlaylistItem item : playlistItems) {
            mappedVids.add(mapVideo(item));
        }

        return mappedVids;
    }

    /**
     * Map an {@link PlaylistItem} to an {@link YouTubeVideo}
     *
     * @param playlistItem {@link PlaylistItem} which will be mapped
     * @return {@link YouTubeVideo} which will be the result of the mapping
     *
     * @since 1.0
     */
    private static YouTubeVideo mapVideo(final PlaylistItem item) {
        return new YouTubeVideo(item.getContentDetails().getVideoId(), item.
                getSnippet().getTitle(), item.getSnippet().getDescription(),
                null);
    }

    /**
     * Request the own {@link Channel} from youtube
     *
     * @return {@link Channel} instance which represents the own channel
     *
     * @throws IOException Can't connect to youtube service.
     *
     * @since 1.0
     */
    private Channel getMyChannel() throws IOException {
        final YouTube.Channels.List channelRequest = _youtube.channels()
                .list("contentDetails");
        channelRequest.setMine(Boolean.TRUE);
        channelRequest.setFields("items/contentDetails, items/id");
        channelRequest.setMaxResults(1L);
        final ChannelListResponse response = channelRequest.execute();

        return response.getItems().get(0);
    }

    /**
     * Creates an request to get {@link PlaylistItem}s for the playlist id. If
     * the playlist id is the id of the uploaded videos than the methode creates
     * an request to get all uploaded videos.
     *
     * @param playlistId {@link String} with the id of the correspondending playlist.
     * @return {@link PlaylistItem} request to get all elements of the playlist.
     *
     * @throws IOException An error occurs while creating the request.
     * 
     * @since 1.0
     */
    private YouTube.PlaylistItems.List createPlaylistItemsRequest(
            final String playlistId) throws IOException {
        final YouTube.PlaylistItems.List request = _youtube.playlistItems().
                list("id,contentDetails,snippet");
        request.setMaxResults(5L);
        request.setPlaylistId(playlistId);
        request.setFields("items(contentDetails/videoId,snippet/title, "
                + "snippet/description, snippet/publishedAt),"
                + "nextPageToken, pageInfo");
        return request;
    }

    /**
     * Gets the ID of the upload playlist. Reads the id of the uploaded video
     * playlist from {@link Channel}.
     *
     * @return ID of the uploaded video playlist
     *
     * @since 1.0
     */
    private String getUploadPlaylistId() {
        return _myChannel.getContentDetails().getRelatedPlaylists().getUploads();
    }
}
