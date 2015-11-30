/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package feedmeyoutube.crawler;

import com.google.api.services.youtube.model.Playlist;
import feedmeyoutubecrawler.crawler.Crawler;
import feedmeyoutubecrawler.crawler.PlaylistCrawler;
import feedmeyoutubecrawler.crawler.YouTubeConnection;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Test class for the {@link feedmeyoutubecrawler.crawler.PlaylistCrawler}.
 * @author Robert Lehmann
 * @since 1.0
 */
public class TestPlaylistCrawler {
    @Test
    public void testGetNextPlaylists() throws IOException {
        final Crawler<Playlist> pc = new PlaylistCrawler(new YouTubeConnection());
        Assert.assertTrue(pc.hasNext());
        final List<Playlist> playlists = pc.getNext();
        Assert.assertEquals(playlists.size(), 1);
        Assert.assertNotNull(playlists.get(0).getId());
    }
}
