// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.splash;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.common.secretTextView.SecretTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.animatedTextView = Utils.findRequiredViewAsType(source, R.id.animatedTextView, "field 'animatedTextView'", SecretTextView.class);
    target.errorView = Utils.findRequiredViewAsType(source, R.id.errorView, "field 'errorView'", RelativeLayout.class);
    target.errorTextView = Utils.findRequiredViewAsType(source, R.id.errorTextView, "field 'errorTextView'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.animatedTextView = null;
    target.errorView = null;
    target.errorTextView = null;
  }
}
