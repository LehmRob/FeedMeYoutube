/* Copyright (C) 2015 Matthias Seifert
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package feedmeyoutubecore.youtube;

import java.util.Date;

/**
 * Data class which represents an Video from youtube.
 *
 * The class contains the following attributes of an common youtube video:
 * <ul>
 *     <li>ID</li>
 *     <li>Title</li>
 *     <li>Description</li>
 *     <li>Upload date</li>
 * </ul>
 *
 * @since 1.0
 * @author Matthias Seifert
 */
public class Video {
    public String VideoId, VideoTitle, VideoDescription;
    public Date UploadDate;

    public Video(String VideoId, String VideoTitle, String VideoDescription, Date UploadDate)
    {
        this.VideoId = VideoId;
        this.VideoTitle = VideoTitle;
        this.VideoDescription = VideoDescription;
        this.UploadDate = UploadDate;
    }

    /**
     * {@inheritDoc }
     * @since 1.0
     */
    @Override
    public String toString() {
        return "YoutubeVideo: \n" +
                "  Title: " + VideoTitle + "\n" +
                "  Description: " + VideoDescription + "\n" +
                "  ID: " + VideoId + "\n" + 
                "  Uploaded at: " +  UploadDate + "\n" +
                "----------------------------------------\n";
    }
}
