package gr.mobile.zisis.pibook.network.parser.videos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisis on 224//18.
 */

public class ScreenCastResponse {

    @SerializedName("screencasts")
    public ArrayList<Screencast> response = null;

    public ArrayList<Screencast> getScreencasts() {
        return response;
    }

    public void setResponse(ArrayList<Screencast> response) {
        this.response = response;
    }

    public String getVideoUrlByContent(String content) {

        for (Screencast screencast : response) {
            if (screencast.getType().equalsIgnoreCase(content)) {
                return screencast.getVideoUrl();
            }
        }
        return null;
    }
}
