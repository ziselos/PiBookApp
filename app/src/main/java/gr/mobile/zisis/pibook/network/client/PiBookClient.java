package gr.mobile.zisis.pibook.network.client;


import gr.mobile.zisis.pibook.common.Definitions;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zisis on 1612//17.
 */

public class PiBookClient {

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Definitions.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static PiBookService getApiService() {
        return getRetrofitInstance().create(PiBookService.class);
    }


}
