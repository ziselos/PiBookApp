// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageGalleryFragment_ViewBinding implements Unbinder {
  private ImageGalleryFragment target;

  @UiThread
  public ImageGalleryFragment_ViewBinding(ImageGalleryFragment target, View source) {
    this.target = target;

    target.mImageView = Utils.findRequiredViewAsType(source, R.id.displayImageView, "field 'mImageView'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageGalleryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImageView = null;
  }
}
