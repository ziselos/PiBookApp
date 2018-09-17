// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecognizedPageActivity_ViewBinding implements Unbinder {
  private RecognizedPageActivity target;

  @UiThread
  public RecognizedPageActivity_ViewBinding(RecognizedPageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RecognizedPageActivity_ViewBinding(RecognizedPageActivity target, View source) {
    this.target = target;

    target.pageRecognizedTextView = Utils.findRequiredViewAsType(source, R.id.pageRecognizedTextView, "field 'pageRecognizedTextView'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecognizedPageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pageRecognizedTextView = null;
  }
}
