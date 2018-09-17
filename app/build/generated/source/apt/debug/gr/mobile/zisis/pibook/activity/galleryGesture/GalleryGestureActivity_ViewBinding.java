// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.victor.loading.book.BookLoading;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryGestureActivity_ViewBinding implements Unbinder {
  private GalleryGestureActivity target;

  @UiThread
  public GalleryGestureActivity_ViewBinding(GalleryGestureActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GalleryGestureActivity_ViewBinding(GalleryGestureActivity target, View source) {
    this.target = target;

    target.largeViewPager = Utils.findRequiredViewAsType(source, R.id.largeImageViewPager, "field 'largeViewPager'", ViewPager.class);
    target.galleryLayout = Utils.findRequiredViewAsType(source, R.id.gallery, "field 'galleryLayout'", LinearLayout.class);
    target.bookLoading = Utils.findRequiredViewAsType(source, R.id.bookloading, "field 'bookLoading'", BookLoading.class);
    target.loaderLayout = Utils.findRequiredViewAsType(source, R.id.loaderLayout, "field 'loaderLayout'", RelativeLayout.class);
    target.errorView = Utils.findRequiredViewAsType(source, R.id.errorView, "field 'errorView'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryGestureActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.largeViewPager = null;
    target.galleryLayout = null;
    target.bookLoading = null;
    target.loaderLayout = null;
    target.errorView = null;
  }
}
