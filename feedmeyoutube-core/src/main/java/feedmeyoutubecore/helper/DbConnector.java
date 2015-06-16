package feedmeyoutubecore.helper;

import feedmeyoutubecore.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    // Configuration
    private static String dbHost = "localhost";
    private static String dbName = "webseite";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static String dbUser = "YtbUser";
    private static String dbPass = "12345"; // Only for testing, for god's sake, secure your Database better than me :O

    public static Connection Connect()
    {
        try {
            Class.forName(dbDriver);

            return DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUser, dbPass);
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
