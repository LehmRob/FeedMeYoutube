/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package feedmeyoutube.crawler;

import feedmeyoutubecore.obj.YouTubeVideo;
import feedmeyoutubecrawler.crawler.Crawler;
import feedmeyoutubecrawler.crawler.VideoCrawler;
import feedmeyoutubecrawler.crawler.YouTubeConnection;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test class for the {@link feedmeyoutubecrawler.crawler.VideoCrawler} class.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class TestVideoCrawler {
    @Test
    public void testGetNextVideos() throws Exception {
        final Crawler<YouTubeVideo> vc;
        vc = new VideoCrawler(new YouTubeConnection());
        Assert.assertTrue("There are more videos", vc.hasNext());
        final List<YouTubeVideo> videos = vc.getNext();
        Assert.assertEquals(vc.getNext().size(), 5);
        videos.stream().forEach(youTubeVideo -> Assert.assertNotNull(youTubeVideo.VideoId));
    }
}