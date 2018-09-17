// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.imangazaliev.circlemenu.CircleMenu;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarTextView = Utils.findRequiredViewAsType(source, R.id.toolbarTextView, "field 'toolbarTextView'", AppCompatTextView.class);
    target.circleMenuLayout = Utils.findRequiredViewAsType(source, R.id.circleMenuLayout, "field 'circleMenuLayout'", CircleMenu.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.toolbarTextView = null;
    target.circleMenuLayout = null;
  }
}
