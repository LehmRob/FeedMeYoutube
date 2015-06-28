package feedmeyoutubecore.obj;

import java.util.Date;

/**
 * Created by Matthias Seifert on 15.06.2015.
 */
public class YouTubeVideo {
    public String VideoId, VideoTitle, VideoDescription;
    public Date UploadDate;

    public YouTubeVideo(String VideoId, String VideoTitle, String VideoDescription, Date UploadDate)
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
