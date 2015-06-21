package feedmeyoutubecore.methods;

import feedmeyoutubecore.Main;
import feedmeyoutubecore.obj.YouTubeVideo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;


public class SaveVideo {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String tableName = "ytvideos";

    public static void Save (YouTubeVideo video, Connection con)
    {
        try {
            // Convert Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uDate = dateFormat.format(video.UploadDate);

            // Create the Statement and make the request
            PreparedStatement statement = con.prepareStatement("INSERT INTO " + tableName +
                                                                "(VideoId, VideoTitle, VideoDescription, UploadDate)" +
                                                                "VALUES(?, ?, ?, ?)");
            statement.setString(1, video.VideoId);
            statement.setString(2, video.VideoTitle);
            statement.setString(3, video.VideoDescription);
            statement.setString(4, uDate);

            int rec = statement.executeUpdate();

            if(rec == 1) Log.info("Insert for Video " + video.VideoTitle + " successful!");

            statement.close();
            con.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
