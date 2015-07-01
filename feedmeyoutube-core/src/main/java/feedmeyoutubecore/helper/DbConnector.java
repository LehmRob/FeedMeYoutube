package feedmeyoutubecore.helper;

import feedmeyoutubecore.AppConfig;
import feedmeyoutubecore.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import org.aeonbits.owner.ConfigFactory;

public class DbConnector {
    // The Logger
    private static final Logger Log = LoggerFactory.getLogger(Main.class);
    private static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class);

    // Configuration
    private static final String dbHost = CONFIG.mysqlHost();
    private static final String dbName = CONFIG.mysqlDb();
    private static final String dbDriver = CONFIG.mysqlDriver();
    private static final String dbUser = CONFIG.mysqlUser();
    private static final String dbPass = CONFIG.mysqlPassword();

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
