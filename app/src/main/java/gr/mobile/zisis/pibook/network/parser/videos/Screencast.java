package gr.mobile.zisis.pibook.network.parser.videos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zisis on 224//18.
 */

public class Screencast implements Parcelable {

    @SerializedName("type")
    public String type;

    @SerializedName("videoUrl")
    public String videoUrl;

    protected Screencast(Parcel in) {
        type = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<Screencast> CREATOR = new Creator<Screencast>() {
        @Override
        public Screencast createFromParcel(Parcel in) {
            return new Screencast(in);
        }

        @Override
        public Screencast[] newArray(int size) {
            return new Screencast[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(videoUrl);
    }
}
