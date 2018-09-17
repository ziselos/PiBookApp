package gr.mobile.zisis.pibook.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.activity.home.HomeActivity;
import gr.mobile.zisis.pibook.common.secretTextView.SecretTextView;
import gr.mobile.zisis.pibook.common.singleton.SingletonClass;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.bookPages.BookPagesToServeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zisis on 74//18.
 */

public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private final static int ANIMATION_IN_MILLIS = 3000;
    private static final long DELAY_MILLIS_AFTER_ANIMATION_END = 500L;

    @BindView(R.id.animatedTextView)
    SecretTextView animatedTextView;

    @BindView(R.id.errorView)
    RelativeLayout errorView;

    @BindView(R.id.errorTextView)
    AppCompatTextView errorTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }

    private void fetchPagesToRecognize() {
        PiBookService piBookService = PiBookClient.getApiService();
        // call pagesToServeApi.json
        Call<BookPagesToServeResponse> call = piBookService.getPiBookPagesToServe();


        call.enqueue(new Callback<BookPagesToServeResponse>() {
            @Override
            public void onResponse(Call<BookPagesToServeResponse> call, Response<BookPagesToServeResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getResponse().get(0).getBookPage());
                    SingletonClass.getInstance().setBookPageArrayList(response.body().getResponse());
                    if (response.body().getResponse() == null) {
                        errorView.setVisibility(View.VISIBLE);
                        errorTextView.setText(R.string.splash_screen_no_pages_error_text);
                    } else {
                        Handler handler = new Handler();
                        startTextAnimation();
                        handler.postDelayed(onNextScreenRunnable, ANIMATION_IN_MILLIS + DELAY_MILLIS_AFTER_ANIMATION_END);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookPagesToServeResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                // in case of something went wrong, set bookPages list null and display message
                errorView.setVisibility(View.VISIBLE);
                errorTextView.setText(R.string.generic_error_text);


            }
        });

    }


    @Override
    public void initLayout() {
        fetchPagesToRecognize();
//        Handler handler = new Handler();
//        startTextAnimation();
//        handler.postDelayed(onNextScreenRunnable, ANIMATION_IN_MILLIS + DELAY_MILLIS_AFTER_ANIMATION_END);

    }

    private void startTextAnimation() {
        animatedTextView.setDuration(ANIMATION_IN_MILLIS);
        animatedTextView.setIsVisible(true);
        animatedTextView.toggle();
    }

    private Runnable onNextScreenRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                startNextActivity();
            }
        }
    };

    private void startNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
