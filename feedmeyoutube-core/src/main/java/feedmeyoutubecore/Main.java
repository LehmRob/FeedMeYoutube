package feedmeyoutubecore;

import feedmeyoutubecore.youtube.Video;
import feedmeyoutubecore.youtube.Playlist;
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
        Video vid = new Video("DemoId1245", "Demo Title", "Demo Description", new Date());
        List<String> vidList = asList(vid.VideoId);
        Playlist plist = new Playlist("PlaylistId2", "Playlist Title", "Playlist Description", vidList);

        // Calling the Methods for testing
        ExistenceChecker.CheckItem(vid);
        ExistenceChecker.CheckItem(plist);
    }
}
