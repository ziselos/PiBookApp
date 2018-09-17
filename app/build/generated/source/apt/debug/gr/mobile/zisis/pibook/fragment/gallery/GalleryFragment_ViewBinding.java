// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.fragment.gallery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryFragment_ViewBinding implements Unbinder {
  private GalleryFragment target;

  @UiThread
  public GalleryFragment_ViewBinding(GalleryFragment target, View source) {
    this.target = target;

    target.imageRecyclerView = Utils.findRequiredViewAsType(source, R.id.imageRecyclerView, "field 'imageRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageRecyclerView = null;
  }
}
