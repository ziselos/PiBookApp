package gr.mobile.zisis.pibook.network.parser.images;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zisis on 2912//17.
 */

public class Image implements Parcelable {

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("image_thumb_url")
    private String image_thumb_url;

    @SerializedName("title")
    private String title;

    @SerializedName("bookpage")
    private String bookpage;

    @SerializedName("caption")
    private String caption;

    public Image(String image_url, String image_thumb_url, String title, String bookpage, String caption) {
        this.image_url = image_url;
        this.image_thumb_url = image_thumb_url;
        this.title = title;
        this.bookpage = bookpage;
        this.caption = caption;
    }

    protected Image(Parcel in) {
        image_url = in.readString();
        image_thumb_url = in.readString();
        title = in.readString();
        bookpage = in.readString();
        caption = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_thumb_url() {
        return image_thumb_url;
    }

    public void setImage_thumb_url(String image_thumb_url) {
        this.image_thumb_url = image_thumb_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookpage() {
        return bookpage;
    }

    public void setBookpage(String bookpage) {
        this.bookpage = bookpage;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image_url);
        parcel.writeString(image_thumb_url);
        parcel.writeString(title);
        parcel.writeString(bookpage);
        parcel.writeString(caption);
    }
}
