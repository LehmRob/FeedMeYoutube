package feedmeyoutubecore;

import feedmeyoutubecore.helper.DbConnector;
import feedmeyoutubecore.methods.SaveVideo;
import feedmeyoutubecore.methods.SavePlaylist;
import feedmeyoutubecore.obj.YouTubePlaylist;
import feedmeyoutubecore.obj.YouTubeVideo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExistenceChecker {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    public static void CheckItem(YouTubeVideo video)
    {
        try {
            // Establish the Database connection
            Connection con = DbConnector.Connect();

            // Create the SELECT Statement
            PreparedStatement statement = con.prepareStatement("SELECT * FROM ytvideos WHERE VideoId = ?");
            statement.setString(1, video.VideoId);
            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                Log.info("Unknown Youtube Video! A new record will be added!");
                SaveVideo.Save(video, con);
            } else Log.info("The Youtube Video with the specific ID " + video.VideoId + " has already been recorded!");

            res.close();
            statement.close();
            con.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CheckItem(YouTubePlaylist playlist)
    {
        try {
            // Establish the Database connection
            Connection con = DbConnector.Connect();

            // Create the SELECT Statement
            PreparedStatement statement = con.prepareStatement("SELECT * FROM ytplaylists WHERE PlaylistId = ?");
            statement.setString(1, playlist.PlaylistId);
            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                Log.info("Unknown Youtube Playlist! A new record will be added!");
                SavePlaylist.Save(playlist, con);
            } else Log.info("The Youtube Playlist with the specific ID " + playlist.PlaylistId + " has already been recorded!");

            res.close();
            statement.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CheckItem(YouTubeVideo video, String PlaylistId)
    {
        // Will be added later
    }
}
