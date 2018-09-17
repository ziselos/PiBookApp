package gr.mobile.zisis.pibook.activity.video;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

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
import gr.mobile.zisis.pibook.activity.recognition.RecognitionActivity;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.videos.ScreenCastResponse;
import gr.mobile.zisis.pibook.network.parser.videos.Screencast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zisis on 252//18.
 */

public class VideoPlayerActivity extends AppCompatActivity implements CameraGestureSensor.Listener, ClickSensor.Listener {

    private final static String TAG = VideoPlayerActivity.class.getSimpleName();

    private final static String ARGUMENTS_PAGE_RECOGNIZED_CONTENT = "arguments_page_recognized_content";

    private String content;

    private ArrayList<Screencast> screencastArrayList;


    @BindView(R.id.videoPlayer)
    YouTubePlayerView videoPlayer;

    @OnClick(R.id.continueReadingButton)
    void onContinueReadingButtonClicked() {
     userFlipPage();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();
        setContentView(R.layout.layout_video);
        ButterKnife.bind(this);
        fetchVideos();
    }

    private void fetchVideos() {

        PiBookService piBookService = PiBookClient.getApiService();
        // call ScreenCastsApi.json
        Call<ScreenCastResponse> call = piBookService.getScreencasts();

        //enque Callback will be call when get response

        call.enqueue(new Callback<ScreenCastResponse>() {
            @Override
            public void onResponse(Call<ScreenCastResponse> call, Response<ScreenCastResponse> response) {
                if (response.isSuccessful()) {
                    screencastArrayList = response.body().getScreencasts();
                    //String remixToDisplay = response.body().getPageRemix(pageRecognized).getRemixUrl();
                    if (content != null) {
                        final String videoToDisplay = response.body().getVideoUrlByContent(content);

                        videoPlayer.initialize(new YouTubePlayerInitListener() {
                            @Override
                            public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady() {
                                        initializedYouTubePlayer.loadVideo(videoToDisplay, 0);
                                    }
                                });
                            }
                        }, true);



                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing to preview", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScreenCastResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                // videoPlayer has its fail view

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtility.checkCameraPermission(this)) {
            LocalOpenCV loader = new LocalOpenCV(VideoPlayerActivity.this, VideoPlayerActivity.this, VideoPlayerActivity.this);
        }
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onSensorClick(ClickSensor caller) {
        userFlipPage();

    }

    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Up");
        userFlipPage();
    }

    @Override
    public void onGestureDown(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Down");
        userFlipPage();
    }

    @Override
    public void onGestureLeft(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Left");
        userFlipPage();
    }

    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Right");
        userFlipPage();
    }

    private void userFlipPage() {
            Log.i(TAG, "Click");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VideoPlayerActivity.this, "User flip page....return to Recognition", Toast.LENGTH_SHORT).show();
                    videoPlayer.release();
                    Intent intent = new Intent(VideoPlayerActivity.this, RecognitionActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
    }

    private void getPassData() {
        Bundle getPassDataBundle = getIntent().getExtras();
        if (getPassDataBundle != null) {
            content = getPassDataBundle.getString(ARGUMENTS_PAGE_RECOGNIZED_CONTENT);
        }
    }
}
