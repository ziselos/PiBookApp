package gr.mobile.zisis.pibook.network.parser.bookPages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zisis on 64//18.
 */

public class BookPage implements Parcelable {

    @SerializedName("bookImage")
    private String bookImage;

    @SerializedName("bookPage")
    private String bookPage;

    @SerializedName("typeOfContent")
    private String typeOfContent;

    @SerializedName("content")
    private String content;

    protected BookPage(Parcel in) {
        bookImage = in.readString();
        bookPage = in.readString();
        typeOfContent = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookImage);
        dest.writeString(bookPage);
        dest.writeString(typeOfContent);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookPage> CREATOR = new Creator<BookPage>() {
        @Override
        public BookPage createFromParcel(Parcel in) {
            return new BookPage(in);
        }

        @Override
        public BookPage[] newArray(int size) {
            return new BookPage[size];
        }
    };

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookPage() {
        return bookPage;
    }

    public void setBookPage(String bookPage) {
        this.bookPage = bookPage;
    }

    public String getTypeOfContent() {
        return typeOfContent;
    }

    public void setTypeOfContent(String typeOfContent) {
        this.typeOfContent = typeOfContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
