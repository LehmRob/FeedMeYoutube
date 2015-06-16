package feedmeyoutubecore;

import feedmeyoutubecore.obj.YouTubeVideo;
import feedmeyoutubecore.helper.DbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.sql.Connection;
import java.text.SimpleDateFormat;


public class SaveVideo {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String tableName = "ytvideos";

    public static void Save (YouTubeVideo video) {
        try {
            // Establish the Database connection
            Connection con = DbConnector.Connect();

            // Convert Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uDate = dateFormat.format(video.UploadDate);

            // Create the Statement and make the request
            Statement st = con.createStatement();
            int rec = st.executeUpdate("INSERT INTO " + tableName + " (VideoId, VideoTitle, VideoDescription, UploadDate)" +
                                       " VALUES('" + video.VideoId + "'," +
                                                "'" + video.VideoTitle + "'," +
                                                "'" + video.VideoDescription + "'," +
                                                "'" + uDate + "')");

            if(rec == 1) Log.info("Insert for " + video.VideoTitle + " successful");

            con.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
