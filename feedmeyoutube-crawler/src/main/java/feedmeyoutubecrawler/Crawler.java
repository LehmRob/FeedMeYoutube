package feedmeyoutubecrawler;

// The MIT License (MIT)
// Copyright (c) 2015 Robert Lehmann
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.


import com.google.common.collect.Lists;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


/**
 * This is the main crawler class which runs the crawler
 * @author Robert Lehmann
 * @since 1.0
 */
public class Crawler {
    private static final String APP_NAME = "FeedMeYoutube";
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
    private static final CrawlerConfig config = ConfigFactory.create(CrawlerConfig.class);

    private final static List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.readonly");

    /**
     * Constructor.
     *
     * @since 1.0
     */
    public Crawler() {}

    /**
     * Runs the main application
     */
    public void run() {
        try {
            Auth.authorize(scopes, "feedmeyoutube");
        } catch (IOException e) {
            LOG.error("Error during authorisation", e);
        }
    }
}
