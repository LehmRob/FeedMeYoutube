package feedmeyoutubecrawler;

/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

import com.google.api.services.youtube.model.Playlist;
import feedmeyoutubecore.Version;
import feedmeyoutubecore.youtube.Video;
import feedmeyoutubecrawler.crawler.Crawler;
import feedmeyoutubecrawler.crawler.PlaylistCrawler;
import feedmeyoutubecrawler.crawler.VideoCrawler;
import feedmeyoutubecrawler.crawler.YouTubeConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Main class which runs the main application.
 * @author Robert Lehmann
 * @since 1.0
 */
public class Main
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg)
    {
        LOG.debug("Starting App in Version {}", Version.VERSION);

        try {
            final Crawler<Video> vc = new VideoCrawler(new YouTubeConnection());
            final Crawler<Playlist> pc = new PlaylistCrawler(new YouTubeConnection());
            if (vc.hasNext()) {
                List<Video> videos = vc.getNext();
                LOG.info("Size of video list {}", videos.size());
                videos.stream().forEach(video -> LOG.info("Video info {}", video.VideoId));
            }
            if (pc.hasNext()) {
                List<Playlist> playlists = pc.getNext();
                LOG.info("Size of Playlist list {}", playlists.size());
                playlists.stream().forEach(playlist -> LOG.info("Playlist info {}", playlist.getId()));
            }

            if (pc.hasNext()) {
                List<Playlist> playlists = pc.getNext();
                LOG.info("Size of Playlist list {}", playlists.size());
                playlists.stream().forEach(playlist -> LOG.info("Playlist info {}", playlist.getSnippet().getTitle()));
            }

        } catch (final IOException e) {
            LOG.error("Can't connect to youtube", e);
        }
    }
}
