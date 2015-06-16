package feedmeyoutubecore;

import feedmeyoutubecore.obj.YouTubeVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SaveVideo {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String dbHost = "localhost";
    private static String dbName = "webseite";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static String dbUser = "YtbUser";
    private static String dbPass = "12345";                         // For testing ;)

    public static void Save (YouTubeVideo video) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUser, dbPass);
            Log.info("Database connection established");

            Statement st = con.createStatement();

            // Convert Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uDate = dateFormat.format(video.UploadDate);

            int rec = st.executeUpdate("INSERT INTO ytvideos (VideoId, VideoTitle, VideoDescription, UploadDate)" +
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
