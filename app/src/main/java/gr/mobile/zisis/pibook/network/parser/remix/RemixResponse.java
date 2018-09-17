package gr.mobile.zisis.pibook.network.parser.remix;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zisis on 1612//17.
 */

public class RemixResponse {

    private boolean pageHasRemix =  false;
    private int remixPosition;

    @SerializedName("remixes")
    private ArrayList<Remix> remixes = null;

    public ArrayList<Remix> getRemixes() {
        return remixes;
    }

    public void setRemixes(ArrayList<Remix> remixes) {
        this.remixes = remixes;
    }

    public Remix getPageRemix(String page) {
        for (Remix remix: remixes) {
            if (remix.getBookpage().equalsIgnoreCase(page)) {
                pageHasRemix = true;
                remixPosition = remixes.indexOf(remix);
                break;
            }
        }

        if (pageHasRemix) {
            return remixes.get(remixPosition);
        } else {
            return null;
        }
    }

    public String getContentByRemixName(String remixName) {
        for (Remix remix : remixes) {
            if (remix.getTitle().equalsIgnoreCase(remixName)) {
                return remix.getRemixUrl();
            }
        }

        return null;
    }
}
