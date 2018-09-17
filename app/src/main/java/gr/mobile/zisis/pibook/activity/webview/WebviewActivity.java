package gr.mobile.zisis.pibook.activity.webview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.victor.loading.book.BookLoading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.washington.cs.touchfreelibrary.sensors.CameraGestureSensor;
import edu.washington.cs.touchfreelibrary.sensors.ClickSensor;
import edu.washington.cs.touchfreelibrary.utilities.LocalOpenCV;
import edu.washington.cs.touchfreelibrary.utilities.PermissionUtility;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.activity.home.HomeActivity;
import gr.mobile.zisis.pibook.activity.recognition.RecognitionActivity;
import gr.mobile.zisis.pibook.activity.video.VideoPlayerActivity;
import gr.mobile.zisis.pibook.common.singleton.SingletonClass;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.remix.Remix;
import gr.mobile.zisis.pibook.network.parser.remix.RemixResponse;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zisis on 2712//17.
 */

public class WebviewActivity extends BaseActivity implements CameraGestureSensor.Listener, ClickSensor.Listener {

    private final static String ARGUMENTS_PAGE_RECOGNIZED = "arguments_page_recognized";

    private final static String ARGUMENTS_PAGE_RECOGNIZED_CONTENT = "arguments_page_recognized_content";

    private String content;

    private final static String TAG = WebviewActivity.class.getSimpleName();

    private ArrayList<Remix> remixArrayList;

    private String pageRecognized;

    String htmlFilename = "web/default.html";

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.bookloading)
    BookLoading bookLoading;

    @BindView(R.id.loaderLayout)
    RelativeLayout loaderLayout;

    @BindView(R.id.errorView)
    RelativeLayout errorView;

    @OnClick(R.id.continueReadingButton)
    void onContinueReadingButtonClicked() {
        startActivity(new Intent(WebviewActivity.this, RecognitionActivity.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();
        setContentView(R.layout.layout_webview);
        fetchRemixes();
    }

    private void fetchRemixes() {
        loaderLayout.setVisibility(View.VISIBLE);
        bookLoading.start();

        PiBookService piBookService = PiBookClient.getApiService();
        // call remixApi.json
        Call<RemixResponse> call = piBookService.getPiBookRemixes();

        //enque Callback will be call when get response

        call.enqueue(new Callback<RemixResponse>() {
            @Override
            public void onResponse(Call<RemixResponse> call, Response<RemixResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getRemixes().get(0).getDescription());// delete after testing
                    remixArrayList = response.body().getRemixes();
                    //String remixToDisplay = response.body().getPageRemix(pageRecognized).getRemixUrl();
                    if (content != null) {
                        String remixToDisplay = response.body().getContentByRemixName(content);
                        webView.setWebViewClient(new CustomWebViewClient());
                        webView.loadUrl(remixToDisplay);
                        //loadHtmlPage(webView);
                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing to preview", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemixResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                loaderLayout.setVisibility(View.GONE);
                bookLoading.stop();
                errorView.setVisibility(View.VISIBLE);

            }
        });


    }

    @Override
    public void initLayout() {

        AssetManager assetManager = getAssets();
        //try to display html content
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtility.checkCameraPermission(this)) {
            LocalOpenCV loader = new LocalOpenCV(WebviewActivity.this, WebviewActivity.this, WebviewActivity.this);
        }
    }


    private void getPassData() {
        Bundle getPassDataBundle = getIntent().getExtras();
        if (getPassDataBundle != null) {
            //pageRecognized = getPassDataBundle.getString(ARGUMENTS_PAGE_RECOGNIZED);
            content = getPassDataBundle.getString(ARGUMENTS_PAGE_RECOGNIZED_CONTENT);
        }
    }


    /**
     * Gets html content from the assets folder.
     */
    private String getHtmlFromAsset() {
        InputStream is;
        StringBuilder builder = new StringBuilder();
        String htmlString = null;
        try {
            is = getAssets().open(htmlFilename);
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                htmlString = builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlString;
    }

    /**
     * Loads html page with the content.
     */
    private void loadHtmlPage(WebView webView) {
        String htmlString = getHtmlFromAsset();
        if (htmlString != null)
            webView.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null);

        else
            Toast.makeText(this, "No such page", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {

    }

    @Override
    public void onGestureDown(CameraGestureSensor caller, long gestureLength) {

    }

    @Override
    public void onGestureLeft(CameraGestureSensor caller, long gestureLength) {
        userFlipPage();

    }

    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {

    }

    @Override
    public void onSensorClick(ClickSensor caller) {

    }


    private void userFlipPage() {
        Log.i(TAG, "Click");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebviewActivity.this, "User flip page....return to Recognition", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WebviewActivity.this, RecognitionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loaderLayout.setVisibility(View.VISIBLE);
            bookLoading.start();
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            bookLoading.stop();
            loaderLayout.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            errorView.setVisibility(View.VISIBLE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

}
