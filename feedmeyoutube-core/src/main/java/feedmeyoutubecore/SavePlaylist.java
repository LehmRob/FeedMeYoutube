package feedmeyoutubecore;

import feedmeyoutubecore.helper.DbConnector;
import feedmeyoutubecore.obj.YouTubePlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;

public class SavePlaylist {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String tableName = "ytplaylists";

    public static void Save (YouTubePlaylist playlist) {
        try {
            // Establish the Database connection
            Connection con = DbConnector.Connect();

            // Create the Statement and make the request
            Statement st = con.createStatement();
            int rec = st.executeUpdate("INSERT INTO " + tableName + " (PlaylistId, PlaylistTitle, PlaylistDescription)" +
                    " VALUES('" + playlist.PlaylistId + "'," +
                    "'" + playlist.PlaylistTitle + "'," +
                    "'" + playlist.PlaylistDescription + "')");

            if(rec == 1) Log.info("Insert for " + playlist.PlaylistTitle + " successful");

            con.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
