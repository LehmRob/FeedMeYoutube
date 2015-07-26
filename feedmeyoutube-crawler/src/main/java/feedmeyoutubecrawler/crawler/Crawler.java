package feedmeyoutubecrawler.crawler;

import feedmeyoutubecore.obj.YouTubeVideo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
/**
 * This is the main crawler class which runs the crawler
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public class Crawler {

    private final VideoCrawler _videoCrawler;
    private final YouTubeConnection _connection;
    
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

    /**
     * Constructor for the {@link Crawler}
     *
     * @throws IOException Error occurs during authorisation of the craweler
     * @since 1.0
     */
    public Crawler() throws IOException {
        _connection = new YouTubeConnection();
        _videoCrawler = new VideoCrawler(_connection);
    }

    /**
     * Crawl the information from the youtube account
     *
     * @throws IOException Can't connect to YouTube.
     * @since 1.0
     */
    public void crawl() throws IOException {
        final List<YouTubeVideo> list = new ArrayList<>();
        
        while (_videoCrawler.hasNext()) {
            System.out.println("Next");
            list.addAll(_videoCrawler.getNextVideos());
        }
        
        list.stream().forEach(video -> {
            LOG.info(video.VideoTitle);
        });
        
        System.out.println(list);
    }
}
