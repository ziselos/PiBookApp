package gr.mobile.zisis.pibook.network.parser.images;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zisis on 2912//17.
 */

public class ImageResponse  {

    @SerializedName("images")
    private ArrayList<Image> images = null;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
