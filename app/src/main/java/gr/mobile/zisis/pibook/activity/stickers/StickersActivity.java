package gr.mobile.zisis.pibook.activity.stickers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;


import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.uploadPhoto.UploadObject;
import gr.mobile.zisis.pibook.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zisis on 1012//17.
 */

public class StickersActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private final static String TAG = StickersActivity.class.getSimpleName();

    private final static String ARGUMENTS_STICKER_RECOGNIZED = "arguments_sticker_recognized";
    private final static String ARGUMENTS_PAGE_RECOGNIZED = "arguments_page_recognized";

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int REQUEST_GALLERY_CODE = 300;
    private static final int READ_REQUEST_CODE = 400;
    private static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private final static int SampleSize = 8;

    private Uri fileUri; // file url to store images

    private Sticker sticker;

    @BindView(R.id.takePhotoButton)
    AppCompatButton takePhotoButton;

    @BindView(R.id.imagePreview)
    AppCompatImageView imagePreview;

    @BindView(R.id.recordVideoButton)
    AppCompatButton recordVideoButton;

    @BindView(R.id.uploadPhotoButton)
    AppCompatButton uploadPhotoButton;

    @BindView(R.id.videoPreview)
    VideoView videoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();
        setContentView(R.layout.layout_stickers);
    }


    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        takePhotoButton.setOnClickListener(takePhotoButtonListener);
        recordVideoButton.setOnClickListener(recordVideoListener);
        uploadPhotoButton.setOnClickListener(uploadPhotoListener);
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your Device does not support camera", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

    }


    private View.OnClickListener takePhotoButtonListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
            captureImage();
        }
    };

    private View.OnClickListener recordVideoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recordVideo();
        }
    };

    private  View.OnClickListener uploadPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadPhotoToServer();
        }
    };

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // device has camera
            return true;
        } else {
            //device has no camera
            return false;
        }
    }


    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fileUri = Utils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE, sticker);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = Utils.getOutputMediaFileUri(MEDIA_TYPE_VIDEO, sticker);

        // set video quality
        // 1- for high quality video
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // video successfully recorded
                //preview the recorded video
                previewVideo();
            } else if (requestCode == RESULT_CANCELED) {
                // user canceled recording
                Toast.makeText(getApplicationContext(), "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            //fileUri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(fileUri, StickersActivity.this);
                File file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(200, TimeUnit.SECONDS)
                        .connectTimeout(200, TimeUnit.SECONDS)
                        .writeTimeout(200, TimeUnit.SECONDS)
                        .build();

                PiBookService piBookService = PiBookClient.getApiService();
                Call<UploadObject> fileUpload = piBookService.uploadFile(fileToUpload, filename);

                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        Toast.makeText(StickersActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                        Toast.makeText(StickersActivity.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Error " + t.getMessage());
                    }
                });
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void previewCapturedImage() {
        try {
            imagePreview.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = SampleSize;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imagePreview.setImageBitmap(bitmap);
            // now i can sent the image in my own server......
            uploadPhotoToServerDirectly();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Previewing recorded video
     */

    private void previewVideo() {
        try {
            // hide image preview
            imagePreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(fileUri.getPath());
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, StickersActivity.this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("StickersActivity -->", "Permission has been denied");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StickersImageTrackingActivity.class);
        intent.putExtra(ARGUMENTS_PAGE_RECOGNIZED, sticker.getPage());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, RESULT_CANCELED);
        finish();
        super.onBackPressed();

    }

    private void getPassData() {
        Bundle getPassDataBundle = this.getIntent().getExtras();
        if (getPassDataBundle != null) {
            sticker = getPassDataBundle.getParcelable(ARGUMENTS_STICKER_RECOGNIZED);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void uploadPhotoToServer() {
        Intent openGalleryIntent = new Intent(/*Intent.ACTION_PICK*/);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    private void uploadPhotoToServerDirectly() {
        String filePath = getRealPathFromURIPath(fileUri, StickersActivity.this);
        File file = new File(filePath);
        int index = file.getAbsolutePath().indexOf("/PiBook");
        String customFileName = file.getAbsolutePath().substring(index);
        Log.d(TAG, "Filename " + file.getName() + "-----" + customFileName);
        //custom file name
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(200, TimeUnit.SECONDS)
                .connectTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .build();

        PiBookService piBookService = PiBookClient.getApiService();
        Call<UploadObject> fileUpload = piBookService.uploadFile(fileToUpload, filename);

        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                Toast.makeText(StickersActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                Toast.makeText(StickersActivity.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "Error " + t.getMessage());
            }
        });
    }
}
