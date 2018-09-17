package gr.mobile.zisis.pibook.common.singleton;

import java.util.ArrayList;

import gr.mobile.zisis.pibook.network.parser.bookPages.BookPage;

/**
 * Created by zisis on 74//18.
 */

/* ------  Use this class in order to save the pages that app needs to recognized ------- */

public class SingletonClass {

    private static volatile SingletonClass mInstance;


    private ArrayList<BookPage> bookPageArrayList = null;

    private SingletonClass() {
        bookPageArrayList = new ArrayList<>();
    }


    public static SingletonClass getInstance() {
        if (mInstance == null) {
            mInstance = new SingletonClass();
        }

        return mInstance;
    }

    public synchronized ArrayList<BookPage> getBookPageArrayList() {
        return bookPageArrayList;
    }

    public synchronized void setBookPageArrayList(ArrayList<BookPage> bookPageArrayList) {
        this.bookPageArrayList = bookPageArrayList;
    }

    public synchronized  ArrayList<String> getBookPageListAsString() {
        ArrayList<String> pagesArrayList = new ArrayList<>();

        for (BookPage bookPage : bookPageArrayList) {
            pagesArrayList.add(bookPage.getBookPage());
        }

        return pagesArrayList;
    }

    public synchronized  String getTypeOfContentToDisplay(String page) {
        for (BookPage bookPage : bookPageArrayList) {
            if (bookPage.getBookPage().equalsIgnoreCase(page)) {
                return bookPage.getTypeOfContent();
            }
        }

        return null;
    }

    public synchronized String getContentToDisplay(String page) {
        for (BookPage bookPage : bookPageArrayList) {
            if (bookPage.getBookPage().equalsIgnoreCase(page)) {
                return bookPage.getContent();
            }
        }
        return null;
    }
}
