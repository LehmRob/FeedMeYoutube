package feedmeyoutubecore.obj;

import java.util.List;

public class YouTubePlaylist {
    public String PlaylistId, PlaylistTitle, PlaylistDescription;
    public List<String> VideoList;

    public YouTubePlaylist(String PlaylistId, String PlaylistTitle, String PlaylistDescription, List<String> VideoList)
    {
        this.PlaylistId = PlaylistId;
        this.PlaylistTitle = PlaylistTitle;
        this.PlaylistDescription = PlaylistDescription;
        this.VideoList = VideoList;
    }
}
