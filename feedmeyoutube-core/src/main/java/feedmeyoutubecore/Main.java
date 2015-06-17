package feedmeyoutubecore;

import feedmeyoutubecore.methods.SavePlaylist;
import feedmeyoutubecore.methods.SaveVideo;
import feedmeyoutubecore.obj.YouTubeVideo;
import feedmeyoutubecore.obj.YouTubePlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import static java.util.Arrays.asList;

public class Main {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg)
    {
        Log.info("Starting core version: {}", Version.VERSION);

        // Creating some test objects
        YouTubeVideo vid = new YouTubeVideo("DemoId1245", "Demo Title", "Demo Description", new Date());
        List<String> vidList = asList(vid.VideoId);
        YouTubePlaylist plist = new YouTubePlaylist("PlaylistId2", "Playlist Title", "Playlist Description", vidList);

        // Calling the Methods for testing
        ExistenceChecker.CheckItem(vid);
        ExistenceChecker.CheckItem(plist);
    }
}
