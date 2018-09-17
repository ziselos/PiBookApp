// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.fragment.gallery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryViewPagerFragment_ViewBinding implements Unbinder {
  private GalleryViewPagerFragment target;

  @UiThread
  public GalleryViewPagerFragment_ViewBinding(GalleryViewPagerFragment target, View source) {
    this.target = target;

    target.imageViewPager = Utils.findRequiredViewAsType(source, R.id.imageViewPager, "field 'imageViewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryViewPagerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageViewPager = null;
  }
}
