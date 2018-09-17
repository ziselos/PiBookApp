package gr.mobile.zisis.pibook.common;

/**
 * Created by zisis on 2612//17.
 */


/*
    Edit this file based on your needs. Replace LOCALHOST_SOURCE
    and REPLACE_SOURCE with your network ip address, REPLACE_TARGET
    with your local ip address.
 */

public class Definitions {

    //Jekyll Urls
    public final static String LOCALHOST_SOURCE = "http://192.168.1.16";

    public final static String REPLACE_SOURCE = "192.168.1.16";

    public final static String REPLACE_TARGET = "0.0.0.0";


    // ISBN of available books for reading

    public static final String PiBookISBN = "978-618-82423-3-3";

    //public final static String  DOMAIN  =  "http://10.0.3.2:4000/";

    // running in device, use the ip which pc is connected to Internet
    public final static String  DOMAIN  = Definitions.LOCALHOST_SOURCE +  ":4000/";


    // PiBook pages that app has to recognize
    /*

    This api keeps the pages that author wants to send in the app for recognition.
    It's content can be changed in git repo.
     */

    public static final String URL_GET_PAGES_NEED_TO_RECOGNIZED = "pagesToServeApi.json";

    //Remix folder

    public final static String URL_GET_REMIX    = "remixApi.json";

    // Images-Gallery

    public final static String URL_GET_IMAGES = "galleryApi.json";

    // Screen casts
    public final static String URL_GET_VIDEOS = "screenCastsApi.json";



    // folder saving user images and videos after sticker recognition

    public final static String STICKERS_DIRECTORY_NAME = "PiBook";

    // upload photos to server

    public static final String SERVER_PATH = LOCALHOST_SOURCE + ":8888/_uploadImages/index.php";



    /* -------------- Bundles ---------------------- */

    public final static String webViewDestination = "webView";
    public final static String stickersDestination = "stickers";
    public final static String videoPlayerDestination = "video";

}
