package gr.mobile.zisis.pibook.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gr.mobile.zisis.pibook.activity.stickers.Sticker;
import gr.mobile.zisis.pibook.common.Definitions;

/**
 * Created by zisis on 1012//17.
 */

public class Utils {

    private static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    //directory name to sore captured images
    private static final String IMAGE_DIRECTORY_NAME = Definitions.STICKERS_DIRECTORY_NAME;

    /**
     * Creating file uri to store image/video
     */
    public static Uri getOutputMediaFileUri(int type, Sticker sticker) {
        // in case of user has android version >= 24
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(getOutputMediaFile(type, sticker));
    }


    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type, Sticker sticker) {

        // External sdcard location
        File mediaStorageDir = getDir(IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Page" + sticker.getPage() + "_" + sticker.getCategory() + File.separator
                    + Definitions.STICKERS_DIRECTORY_NAME + "_" + sticker.getPage() + "_" + sticker.getCategory() + "_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Page" + sticker.getPage() + "_" + sticker.getCategory() + File.separator
                    + Definitions.STICKERS_DIRECTORY_NAME + "_" + sticker.getPage() + "_" + sticker.getCategory()  + "_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        try {
            if (mediaFile != null) {
                mediaFile.getParentFile().mkdirs();
                mediaFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaFile;

    }

    private static File getDir(String directory) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), directory);

    }

}
