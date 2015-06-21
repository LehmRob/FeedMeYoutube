package feedmeyoutubecore.methods;

import feedmeyoutubecore.Main;
import feedmeyoutubecore.obj.YouTubePlaylist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SavePlaylist {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String tableName = "ytplaylists";

    public static void Save (YouTubePlaylist playlist, Connection con)
    {
        try {
            // Create the Statement and make the request
            PreparedStatement statement = con.prepareStatement("INSERT INTO " + tableName +
                                                                "(PlaylistId, PlaylistTitle, PlaylistDescription) +" +
                                                                "VALUES (?, ?, ?)");
            statement.setString(1, playlist.PlaylistId);
            statement.setString(2, playlist.PlaylistTitle);
            statement.setString(3, playlist.PlaylistDescription);

            int rec = statement.executeUpdate();

            if(rec == 1) Log.info("Insert for Playlist " + playlist.PlaylistTitle + " successful");

            statement.close();
            con.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
