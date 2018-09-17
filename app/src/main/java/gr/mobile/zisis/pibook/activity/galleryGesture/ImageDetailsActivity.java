package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.washington.cs.touchfreelibrary.sensors.CameraGestureSensor;
import edu.washington.cs.touchfreelibrary.sensors.ClickSensor;
import edu.washington.cs.touchfreelibrary.utilities.LocalOpenCV;
import edu.washington.cs.touchfreelibrary.utilities.PermissionUtility;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.activity.recognition.RecognitionActivity;
import gr.mobile.zisis.pibook.common.Definitions;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 81//18.
 */

public class ImageDetailsActivity extends BaseActivity implements CameraGestureSensor.Listener, ClickSensor.Listener {

    private final static String TAG = ImageDetailsActivity.class.getSimpleName();

    private final static String ARGUMNETS_IMAGE = "arguments_image";

    @BindView(R.id.imageTitle)
    AppCompatTextView imageTitle;

    @BindView(R.id.imageImageView)
    AppCompatImageView imageImageView;

    @BindView(R.id.imageCaption)
    AppCompatTextView imageCaption;

    private Image image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();
        setContentView(R.layout.layout_image_details);
        ButterKnife.bind(this);
        initLayout();
    }

    public void initLayout() {
        imageTitle.setText(image.getTitle());
        imageCaption.setText(image.getCaption());
        //emulator
        //String imageLocalhost = image.getImage_url().replace("localhost", "10.0.3.2");
        //device
        String imageLocalhost = image.getImage_url().replace(Definitions.REPLACE_TARGET, Definitions.REPLACE_SOURCE);
        Glide.with(getApplicationContext())
                .load(imageLocalhost)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageImageView);

    }

    private void getPassData() {
        Bundle getPassDataBundle = getIntent().getExtras();
        if (getPassDataBundle != null) {
            image = getPassDataBundle.getParcelable(ARGUMNETS_IMAGE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtility.checkCameraPermission(this)) {
            LocalOpenCV loader = new LocalOpenCV(ImageDetailsActivity.this, ImageDetailsActivity.this, ImageDetailsActivity.this);
        }
    }

    @Override
    public void onSensorClick(ClickSensor caller) {
        Log.i(TAG, "Click");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageDetailsActivity.super.onBackPressed();
            }
        });
    }

    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {

    }

    @Override
    public void onGestureDown(CameraGestureSensor caller, long gestureLength) {

    }

    @Override
    public void onGestureLeft(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Left");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ImageDetailsActivity.this, GalleryGestureActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Left");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ImageDetailsActivity.this, GalleryGestureActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
