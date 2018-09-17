// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.fragment.gallery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryDetailsFragment_ViewBinding implements Unbinder {
  private GalleryDetailsFragment target;

  @UiThread
  public GalleryDetailsFragment_ViewBinding(GalleryDetailsFragment target, View source) {
    this.target = target;

    target.imageTitle = Utils.findRequiredViewAsType(source, R.id.imageTitle, "field 'imageTitle'", AppCompatTextView.class);
    target.imageCaption = Utils.findRequiredViewAsType(source, R.id.imageCaption, "field 'imageCaption'", AppCompatTextView.class);
    target.imageImageView = Utils.findRequiredViewAsType(source, R.id.imageImageView, "field 'imageImageView'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryDetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageTitle = null;
    target.imageCaption = null;
    target.imageImageView = null;
  }
}
