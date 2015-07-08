package feedmeyoutubecrawler;

/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

import feedmeyoutubecrawler.crawler.Crawler;
import feedmeyoutubecore.Version;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final Crawler crawler;
        try {
            crawler = new Crawler();
            System.out.println(crawler.getUploadedVideos());
        } catch (final IOException ex) {
            LOG.error("Error during the initialisation of the crawler", ex);
        }
    }
}
