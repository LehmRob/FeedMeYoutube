package feedmeyoutubecore;

import feedmeyoutubecore.obj.YouTubeVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Main {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg)
    {
        Log.info("Starting core version: {}", Version.VERSION);

        // Creating a test Object
        YouTubeVideo vid = new YouTubeVideo("DemoId1235", "Demo Title", "Demo Description", new Date());

        // Calling the Methods for testing
        SaveVideo.Save(vid);
    }
}
