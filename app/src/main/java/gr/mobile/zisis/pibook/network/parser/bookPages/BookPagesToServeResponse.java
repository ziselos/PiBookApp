package gr.mobile.zisis.pibook.network.parser.bookPages;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zisis on 64//18.
 */

public class BookPagesToServeResponse {

    @SerializedName("bookPages")
    private ArrayList<BookPage> response = null;

    public ArrayList<BookPage> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<BookPage> response) {
        this.response = response;
    }
}
