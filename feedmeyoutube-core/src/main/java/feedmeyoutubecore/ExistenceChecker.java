package feedmeyoutubecore;

import feedmeyoutubecore.helper.DbConnector;
import feedmeyoutubecore.methods.SaveVideo;
import feedmeyoutubecore.methods.SavePlaylist;
import feedmeyoutubecore.youtube.Playlist;
import feedmeyoutubecore.youtube.Video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExistenceChecker {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    public static void CheckItem(Video video)
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

    public static void CheckItem(Playlist playlist)
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
            } else {
                Log.info("The Youtube Playlist with the specific ID " + playlist.PlaylistId + " has already been recorded!");
                Log.info("Refreshing all the containing Videos f�r the Playlist " + playlist.PlaylistTitle);

                RefreshPlaylist(playlist);

            }

            res.close();
            statement.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void RefreshPlaylist(Playlist playlist)
    {
        for (final String videoId : playlist.VideoList)
        {
            CheckItem(videoId, playlist.PlaylistId);
        }
    }

    private static void CheckItem(String videoId, String playlistId)
    {
        try {
            // Establish the Database connection
            Connection con = DbConnector.Connect();

            // Create the SELECT Statement
            PreparedStatement statement = con.prepareStatement("SELECT * FROM ytplaylistitems " +
                                                               "WHERE PlaylistId = ? " +
                                                               "AND VideoId = ?");
            statement.setString(1, playlistId);
            statement.setString(2, videoId);
            ResultSet res = statement.executeQuery();

            if (!res.next()) {
                Log.info("Unknown Youtube Playlist! A new record will be added!");
                // TODO: Add SavePlaylistItem Method
            } else Log.info("The Video with the ID " + videoId + " is already registered for the Playlist with the ID " + playlistId);

            res.close();
            statement.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
