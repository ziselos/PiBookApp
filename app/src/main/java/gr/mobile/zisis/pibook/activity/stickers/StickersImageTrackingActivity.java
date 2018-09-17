package gr.mobile.zisis.pibook.activity.stickers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wikitude.NativeStartupConfiguration;
import com.wikitude.WikitudeSDK;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.ImageTarget;
import com.wikitude.tracker.ImageTracker;
import com.wikitude.tracker.ImageTrackerListener;
import com.wikitude.tracker.TargetCollectionResource;
import com.wikitude.tracker.TargetCollectionResourceLoadingCallback;

import gr.mobile.zisis.pibook.WikitudeSDKConstants;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.rendering.external.CustomSurfaceView;
import gr.mobile.zisis.pibook.rendering.external.Driver;
import gr.mobile.zisis.pibook.rendering.external.GLRenderer;
import gr.mobile.zisis.pibook.rendering.external.StrokedRectangle;
import gr.mobile.zisis.pibook.utils.DropDownAlert;


/**
 * Created by zisis on 1012//17.
 */

public class StickersImageTrackingActivity  extends BaseActivity implements ImageTrackerListener, ExternalRendering {

    private final static String ARGUMENTS_STICKER_RECOGNIZED = "arguments_sticker_recognized";
    private final static String ARGUMENTS_PAGE_RECOGNIZED = "arguments_page_recognized";

    private static final String TAG = "SimpleClientTracking";
    private WikitudeSDK mWikitudeSDK;
    private gr.mobile.zisis.pibook.rendering.external.CustomSurfaceView mView;
    private Driver mDriver;
    private GLRenderer mGLRenderer;

    private TargetCollectionResource mTargetCollectionResource;
    private DropDownAlert mDropDownAlert;

    private String pageRecognized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPassData();

        mWikitudeSDK = new WikitudeSDK(this);
        NativeStartupConfiguration startupConfiguration = new NativeStartupConfiguration();
        startupConfiguration.setLicenseKey(WikitudeSDKConstants.WIKITUDE_SDK_KEY);
        startupConfiguration.setCameraPosition(CameraSettings.CameraPosition.BACK);
        startupConfiguration.setCameraResolution(CameraSettings.CameraResolution.AUTO);

        mWikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);

        mTargetCollectionResource = mWikitudeSDK.getTrackerManager().createTargetCollectionResource("file:///android_asset/PiBook.wtc", new TargetCollectionResourceLoadingCallback() {
            @Override
            public void onError(int errorCode, String errorMessage) {
                Log.v(TAG, "Failed to load target collection resource. Reason: " + errorMessage);
            }

            @Override
            public void onFinish() {
                mWikitudeSDK.getTrackerManager().createImageTracker(mTargetCollectionResource, StickersImageTrackingActivity.this, null);
            }
        });

        mDropDownAlert = new DropDownAlert(this);
        mDropDownAlert.setText("Scan Sticker:");
        mDropDownAlert.addImages("mailForAssets.jpg");
        mDropDownAlert.addImages("queryForAssets.jpg");
        mDropDownAlert.addImages("todoForAssets.jpg");
        mDropDownAlert.addImages("codeForAssets.jpg");
        mDropDownAlert.addImages("readForAssets.jpg");
        mDropDownAlert.setTextWeight(0.5f);
        mDropDownAlert.show();
    }

    @Override
    public void initLayout() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWikitudeSDK.onResume();
        mView.onResume();
        mDriver.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWikitudeSDK.onPause();
        mView.onPause();
        mDriver.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWikitudeSDK.clearCache();
        mWikitudeSDK.onDestroy();
    }

    @Override
    public void onRenderExtensionCreated(final RenderExtension renderExtension) {
        mGLRenderer = new GLRenderer(renderExtension);
        mView = new CustomSurfaceView(getApplicationContext(), mGLRenderer);
        mDriver = new Driver(mView, 30);
        setContentView(mView);
    }

    @Override
    public void onTargetsLoaded(ImageTracker tracker) {
        Log.v(TAG, "Image tracker loaded");
    }

    @Override
    public void onErrorLoadingTargets(ImageTracker tracker, int errorCode, final String errorMessage) {
        Log.v(TAG, "Unable to load image tracker. Reason: " + errorMessage);
    }

    @Override
    public void onImageRecognized(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Recognized target " + target.getName());
        mDropDownAlert.dismiss();

        StrokedRectangle strokedRectangle = new StrokedRectangle(StrokedRectangle.Type.STANDARD);
        mGLRenderer.setRenderablesForKey(target.getName() + target.getUniqueId(), strokedRectangle, null);
    }

    @Override
    public void onImageTracked(ImageTracker tracker, final ImageTarget target) {
        StrokedRectangle strokedRectangle = (StrokedRectangle)mGLRenderer.getRenderableForKey(target.getName() + target.getUniqueId());

        if (strokedRectangle != null) {
            strokedRectangle.projectionMatrix = target.getProjectionMatrix();
            strokedRectangle.viewMatrix = target.getViewMatrix();

            strokedRectangle.setXScale(target.getTargetScale().x);
            strokedRectangle.setYScale(target.getTargetScale().y);
        }

        //after sticker recognition start StickersActivity for saving image or video in appropriate folder
        //pass sticker object (page and category-name)
        Sticker sticker = new Sticker(pageRecognized, target.getName());
        Bundle passDataBundle = new Bundle();
        passDataBundle.putParcelable(ARGUMENTS_STICKER_RECOGNIZED, sticker);
        Intent intent = new Intent(StickersImageTrackingActivity.this, StickersActivity.class);
        intent.putExtras(passDataBundle);
        startActivity(intent);
    }

    @Override
    public void onImageLost(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Lost target " + target.getName());
        mGLRenderer.removeRenderablesForKey(target.getName() + target.getUniqueId());
    }

    @Override
    public void onExtendedTrackingQualityChanged(ImageTracker tracker, final ImageTarget target, final int oldTrackingQuality, final int newTrackingQuality) {

    }

    private void getPassData() {
        Bundle getPassDataBundle = getIntent().getExtras();
        if (getPassDataBundle != null) {
            pageRecognized = getPassDataBundle.getString(ARGUMENTS_PAGE_RECOGNIZED);
        }
    }
}