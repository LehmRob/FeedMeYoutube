/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package feedmeyoutubecrawler.crawler;

import java.io.IOException;
import java.util.List;

/**
 * This interface defines the methodes for the diffrent crawler.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
public interface Crawler<T> {
    /**
     * Check if there are more elements to crawl.
     *
     * This methode looks if there are more elements availible at youtube which can be crawled by
     * the application
     * @return {@code true} if there are more elements to crawl, else return {@code false}.
     * @since 1.0
     */
    boolean hasNext();

    /**
     * Gets a list of next elements from the web service.
     *
     * This methode returns a list of crawled elements from the YouTube web service.
     * @return {@link List} of elements from the type {@link T}
     * @throws IOException Can't crawl the next elements from the webservice
     * @since 1.0
     */
    List<T> getNext() throws IOException;
}
