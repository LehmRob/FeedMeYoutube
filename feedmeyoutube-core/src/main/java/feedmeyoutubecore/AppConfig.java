/* Copyright (C) 2015 Robert Lehmann <lehmrob@gmail.com>
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package feedmeyoutubecore;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;

/**
 * Interface for the configuration.
 *
 * @author Robert Lehmann
 * @since 1.0
 */
@Config.Sources({"file:~/.feedmeyoutube.config",
    "classpath:feedmeyoutube.config"})
public interface AppConfig extends Config {
    @Key("youtube.userId")
    String userId();

    @Key("youtube.channelId")
    String channelId();

    @Key("mysql.user")
    String mysqlUser();
    
    @Key("mysql.password")
    String mysqlPassword();
    
    @Key("mysql.host")
    String mysqlHost();
    
    @Key("mysql.driver")
    String mysqlDriver();
    
    @Key("mysql.db")
    String mysqlDb();
}
