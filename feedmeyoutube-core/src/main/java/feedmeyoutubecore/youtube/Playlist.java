/* Copyright (C) 2015 Matthias Seifert
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package feedmeyoutubecore.youtube;

import java.util.List;

/**
 * Data class which represents an playlist from youtube.
 *
 * The class contains the following attributes of an common plalyist on youtube
 * <ul>
 *     <li>ID</li>
 *     <li>Titiel</li>
 *     <li>Decription</li>
 *     <li>A list of videos which are in the playlist</li>
 * </ul>
 *
 * @since 1.0
 */
public class Playlist {
    public String PlaylistId, PlaylistTitle, PlaylistDescription;
    public List<String> VideoList;

    public Playlist(String PlaylistId, String PlaylistTitle, String PlaylistDescription, List<String> VideoList)
    {
        this.PlaylistId = PlaylistId;
        this.PlaylistTitle = PlaylistTitle;
        this.PlaylistDescription = PlaylistDescription;
        this.VideoList = VideoList;
    }
}
