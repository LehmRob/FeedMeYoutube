package feedmeyoutubecrawler;

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
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    private static final String APP_NAME = "feedmeyoutube";
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
    private static final CrawlerConfig config = ConfigFactory.create(
            CrawlerConfig.class);

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
    }

    public List<YouTubeVideo> getUploadedVideos() throws IOException {
        List<YouTubeVideo> myVideos = new ArrayList<>();
        final YouTube.Channels.List channelRequ = _youtube.channels().list(
                "contentDetails");
        channelRequ.setMine(Boolean.TRUE);
        channelRequ.setFields("items/contentDetails, items/id, nextPageToken,pageInfo");
        final ChannelListResponse response = channelRequ.execute();
        final List<Channel> channels;

        if ((channels = response.getItems()) != null) {
            final Channel myChannel = channels.get(0);

            LOG.debug("My Channel ID {}", myChannel.getId());

            final String uploadPlaylistId = myChannel.getContentDetails().
                    getRelatedPlaylists().getUploads();
            final List<PlaylistItem> playlistItems = new ArrayList<>();
            final YouTube.PlaylistItems.List playlistItemsRequest = _youtube.
                    playlistItems().list("id,contentDetails,snippet");
            playlistItemsRequest.setPlaylistId(uploadPlaylistId);
            playlistItemsRequest.setFields(
                    "items(contentDetails/videoId,snippet/title, "
                            + "snippet/description, snippet/publishedAt),"
                    + "nextPageToken, pageInfo");

            String nextToken = "";
            do {
                playlistItemsRequest.setPageToken(nextToken);
                PlaylistItemListResponse playlistResponse
                        = playlistItemsRequest.execute();
                playlistItems.addAll(playlistResponse.getItems());
                nextToken = playlistResponse.getNextPageToken();
            } while (nextToken != null);

            myVideos = mapVideos(playlistItems);
        }

        return myVideos;
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
        return new YouTubeVideo(item.getContentDetails().getVideoId(), item.getSnippet().
                getTitle(), item.getSnippet().getDescription(), null);
    }
}
