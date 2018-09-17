package gr.mobile.zisis.pibook.activity.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import butterknife.BindView;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.recognition.RecognitionActivity;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.activity.recognition.RecognitionStickersActivity;
import gr.mobile.zisis.pibook.common.Definitions;

/**
 * Created by zisis on 912//17.
 */

public class HomeActivity extends BaseActivity implements CircleMenu.OnItemClickListener {

    private final static String ARGUMENTS_IMAGE_RECOGNITION_SCOPE = "arguments_image_recognition_scope";
    private Bundle passDataBundle;


    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.toolbarTextView)
    AppCompatTextView toolbarTextView;

    @BindView(R.id.circleMenuLayout)
    CircleMenu circleMenuLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_with_circle);
        initToolbar(toolbar);
        //grant permissions
        grantPermissionsForPiBook();

    }

    @Override
    public void initLayout() {
        toolbarTextView.setText(getResources().getText(R.string.toolbar_home_title_text));
        circleMenuLayout.setOnItemClickListener(this);
        passDataBundle = new Bundle();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[2])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("PiBook app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(HomeActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        Toast.makeText(getBaseContext(), "PiBook app got All Permissions", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void grantPermissionsForPiBook() {
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permissionsRequired[2])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("PiBook app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(HomeActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("PiBook app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Camera and Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(HomeActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.apply();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onItemClick(CircleMenuButton menuButton) {
        switch (menuButton.getId()) {
//            case R.id.imageGalleryButton:
//                startActivity(new Intent(HomeActivity.this, GalleryToViewPagerActivity.class));
//                break;
            case R.id.startReadingButton:
                startActivity(new Intent(HomeActivity.this, RecognitionActivity.class));
                break;
//            case R.id.webViewButton:
//                passDataBundle.putString(ARGUMENTS_IMAGE_RECOGNITION_SCOPE, Definitions.webViewDestination);
//                Intent intentWebView = new Intent(HomeActivity.this, WebviewActivity.class);
//                intentWebView.putExtras(passDataBundle);
//                startActivity(intentWebView);
//                break;
            case R.id.stickersButton:
                passDataBundle.putString(ARGUMENTS_IMAGE_RECOGNITION_SCOPE, Definitions.stickersDestination);
                Intent intentStickers = new Intent(HomeActivity.this, RecognitionStickersActivity.class);
                intentStickers.putExtras(passDataBundle);
                startActivity(intentStickers);
                break;
//            case R.id.handGesturesButton:
//                startActivity(new Intent(HomeActivity.this, GalleryGestureActivity.class));
//                break;
            default:

        }
    }
}
