package gr.mobile.zisis.pibook.callbacks;

import android.view.View;

import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 3112//17.
 */

public interface GalleryItemListener {

    void onGalleryItemClicked(View sharedView, int adapterPosition, Image image);
}
