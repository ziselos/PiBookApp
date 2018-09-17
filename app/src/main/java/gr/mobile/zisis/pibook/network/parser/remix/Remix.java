package gr.mobile.zisis.pibook.network.parser.remix;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zisis on 1612//17.
 */


public class Remix implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("codepen")
    private String codepen;

    @SerializedName("description")
    private String description;

    @SerializedName("bookpage")
    private String bookpage;

    @SerializedName("remix_url")
    private String remixUrl;

    protected Remix(Parcel in) {
        title = in.readString();
        codepen = in.readString();
        description = in.readString();
        bookpage = in.readString();
        remixUrl = in.readString();
    }

    public static final Creator<Remix> CREATOR = new Creator<Remix>() {
        @Override
        public Remix createFromParcel(Parcel in) {
            return new Remix(in);
        }

        @Override
        public Remix[] newArray(int size) {
            return new Remix[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCodepen() {
        return codepen;
    }

    public void setCodepen(String codepen) {
        this.codepen = codepen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookpage() {
        return bookpage;
    }

    public void setBookpage(String bookpage) {
        this.bookpage = bookpage;
    }

    public String getRemixUrl() {
        return remixUrl;
    }

    public void setRemixUrl(String remixUrl) {
        this.remixUrl = remixUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(codepen);
        parcel.writeString(description);
        parcel.writeString(bookpage);
        parcel.writeString(remixUrl);
    }
}
