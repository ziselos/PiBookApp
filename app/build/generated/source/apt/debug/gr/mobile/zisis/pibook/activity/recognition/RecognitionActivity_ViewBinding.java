// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.recognition;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecognitionActivity_ViewBinding implements Unbinder {
  private RecognitionActivity target;

  @UiThread
  public RecognitionActivity_ViewBinding(RecognitionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RecognitionActivity_ViewBinding(RecognitionActivity target, View source) {
    this.target = target;

    target.startButton = Utils.findRequiredViewAsType(source, R.id.startButton, "field 'startButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecognitionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.startButton = null;
  }
}
