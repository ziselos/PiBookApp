// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.stickers;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.VideoView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StickersActivity_ViewBinding implements Unbinder {
  private StickersActivity target;

  @UiThread
  public StickersActivity_ViewBinding(StickersActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StickersActivity_ViewBinding(StickersActivity target, View source) {
    this.target = target;

    target.takePhotoButton = Utils.findRequiredViewAsType(source, R.id.takePhotoButton, "field 'takePhotoButton'", AppCompatButton.class);
    target.imagePreview = Utils.findRequiredViewAsType(source, R.id.imagePreview, "field 'imagePreview'", AppCompatImageView.class);
    target.recordVideoButton = Utils.findRequiredViewAsType(source, R.id.recordVideoButton, "field 'recordVideoButton'", AppCompatButton.class);
    target.uploadPhotoButton = Utils.findRequiredViewAsType(source, R.id.uploadPhotoButton, "field 'uploadPhotoButton'", AppCompatButton.class);
    target.videoPreview = Utils.findRequiredViewAsType(source, R.id.videoPreview, "field 'videoPreview'", VideoView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StickersActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.takePhotoButton = null;
    target.imagePreview = null;
    target.recordVideoButton = null;
    target.uploadPhotoButton = null;
    target.videoPreview = null;
  }
}
