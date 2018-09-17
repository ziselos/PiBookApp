package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;

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
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.images.Image;
import gr.mobile.zisis.pibook.network.parser.images.ImageResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by zisis on 81//18.
 */

public class GalleryGestureActivity extends BaseActivity implements CameraGestureSensor.Listener, ClickSensor.Listener {

    private final static String TAG = GalleryGestureActivity.class.getSimpleName();

    private final static String ARGUMENTS_IMAGE = "arguments_image";

    private ArrayList<Image> imageArrayList;


    @BindView(R.id.largeImageViewPager)
    ViewPager largeViewPager;

    @BindView(R.id.gallery)
    LinearLayout galleryLayout;

    @BindView(R.id.bookloading)
    BookLoading bookLoading;

    @BindView(R.id.loaderLayout)
    RelativeLayout loaderLayout;

    @BindView(R.id.errorView)
    RelativeLayout errorView;

    private CustomFragmentPagerAdapter customFragmentPageAdapter;
    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gallery_alter);
        ButterKnife.bind(this);
        fetchImages();

    }

    @Override
    public void initLayout() {

    }

    private void fetchImages() {
        loaderLayout.setVisibility(View.VISIBLE);
        bookLoading.start();

        PiBookService piBookService = PiBookClient.getApiService();
        //call galleryApi.json
        Call<ImageResponse> call = piBookService.getPiBookImages();

        //enque Callback will be call when get response
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    bookLoading.stop();
                    loaderLayout.setVisibility(View.GONE);
                    imageArrayList = response.body().getImages();
                    setUpGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                loaderLayout.setVisibility(View.GONE);
                bookLoading.stop();
                errorView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUpGallery() {
        customFragmentPageAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), imageArrayList);
        largeViewPager.setAdapter(customFragmentPageAdapter);
        for (int j = 0; j < imageArrayList.size(); j++) {
            String imageThumbUrlLocalhost = imageArrayList.get(j).getImage_thumb_url().replace(Definitions.REPLACE_TARGET, Definitions.REPLACE_SOURCE);
            AppCompatImageView addImageView = new AppCompatImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(imageThumbUrlLocalhost)
                     .override(150, 120)
                    .fitCenter()
                    .into(addImageView);
            addImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            final int indexJ = j;
            addImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //currentIndex = indexJ;
                    Toast.makeText(GalleryGestureActivity.this, "Current index " + indexJ, Toast.LENGTH_LONG).show();
                    largeViewPager.setCurrentItem(indexJ);
                }
            });
            galleryLayout.addView(addImageView);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PermissionUtility.checkCameraPermission(this)) {
            LocalOpenCV loader = new LocalOpenCV(GalleryGestureActivity.this, GalleryGestureActivity.this, GalleryGestureActivity.this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onSensorClick(ClickSensor caller) {
        Log.i(TAG, "Click");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GalleryGestureActivity.this, "Click Event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GalleryGestureActivity.this, ImageDetailsActivity.class);
                Bundle passDataBundle = new Bundle();
                Image image = imageArrayList.get(currentIndex);
                passDataBundle.putParcelable(ARGUMENTS_IMAGE, image);
                intent.putExtras(passDataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Click");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GalleryGestureActivity.this, "Gesture Up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GalleryGestureActivity.this, RecognitionActivity.class);
                startActivity(intent);
            }
        });

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
                Toast.makeText(GalleryGestureActivity.this, "Hand Motion Left", Toast.LENGTH_SHORT).show();
                if (currentIndex < imageArrayList.size() - 1) {
                    ++currentIndex;
                }
                largeViewPager.setCurrentItem(currentIndex);
            }
        });

    }

    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {
        Log.i(TAG, "Right");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GalleryGestureActivity.this, "Hand Motion Right", Toast.LENGTH_SHORT).show();
                if (currentIndex > 0) {
                    --currentIndex;
                }
                largeViewPager.setCurrentItem(currentIndex);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GalleryGestureActivity.this, RecognitionActivity.class);
        startActivity(intent);
        finish();
    }
}
