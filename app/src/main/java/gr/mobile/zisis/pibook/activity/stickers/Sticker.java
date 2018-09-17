package gr.mobile.zisis.pibook.activity.stickers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zisis on 2612//17.
 */

public class Sticker implements Parcelable {

    public Sticker(String page, String category) {
        this.page = page;
        this.category = category;
    }

    private String page;

    private String category;

    protected Sticker(Parcel in) {
        page = in.readString();
        category = in.readString();
    }

    public static final Creator<Sticker> CREATOR = new Creator<Sticker>() {
        @Override
        public Sticker createFromParcel(Parcel in) {
            return new Sticker(in);
        }

        @Override
        public Sticker[] newArray(int size) {
            return new Sticker[size];
        }
    };

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(page);
        parcel.writeString(category);
    }
}
