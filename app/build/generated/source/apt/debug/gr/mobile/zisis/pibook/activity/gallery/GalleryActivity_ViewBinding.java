// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.gallery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryActivity_ViewBinding implements Unbinder {
  private GalleryActivity target;

  @UiThread
  public GalleryActivity_ViewBinding(GalleryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GalleryActivity_ViewBinding(GalleryActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarTextView = Utils.findRequiredViewAsType(source, R.id.toolbarTextView, "field 'toolbarTextView'", AppCompatTextView.class);
    target.imageRecyclerView = Utils.findRequiredViewAsType(source, R.id.imageRecyclerView, "field 'imageRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.toolbarTextView = null;
    target.imageRecyclerView = null;
  }
}
