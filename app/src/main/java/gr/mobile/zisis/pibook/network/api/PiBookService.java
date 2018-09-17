package gr.mobile.zisis.pibook.network.api;


import gr.mobile.zisis.pibook.common.Definitions;
import gr.mobile.zisis.pibook.network.parser.bookPages.BookPagesToServeResponse;
import gr.mobile.zisis.pibook.network.parser.images.ImageResponse;
import gr.mobile.zisis.pibook.network.parser.remix.Remix;
import gr.mobile.zisis.pibook.network.parser.remix.RemixResponse;
import gr.mobile.zisis.pibook.network.parser.videos.ScreenCastResponse;
import gr.mobile.zisis.pibook.uploadPhoto.UploadObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by zisis on 1612//17.
 */

public interface PiBookService {

     /* -----------------  Book pages that have extra content to show  ----------------------------------------- */
    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of PiBook
   pages to use
   */

    @GET(Definitions.URL_GET_PAGES_NEED_TO_RECOGNIZED)
    Call<BookPagesToServeResponse> getPiBookPagesToServe();


    /* -----------------    Remix  ----------------------------------------- */
    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of PiBook
   site Remixes
   */
    @GET(Definitions.URL_GET_REMIX)
    Call<RemixResponse> getPiBookRemixes();


    /* -----------------    Gallery  ----------------------------------------- */
    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of PiBook
   site Images
   */

    @GET(Definitions.URL_GET_IMAGES)
    Call<ImageResponse> getPiBookImages();


    /* -----------------    Screen Casts  ----------------------------------------- */
    /*
   Retrofit get annotation with our URL
   And our method that will return us screen casts of PiBook
   site screen casts
   */

    @GET(Definitions.URL_GET_VIDEOS)
    Call<ScreenCastResponse> getScreencasts();


    /* -----------------    Upload user photos to server  ----------------------------------------- */
    /*
   Retrofit get annotation with our URL
   And our method that will upload user photo to server
   */

    @Multipart
    @POST(Definitions.SERVER_PATH)
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);


}
