// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.webview;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.victor.loading.book.BookLoading;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WebviewActivity_ViewBinding implements Unbinder {
  private WebviewActivity target;

  private View view2131230783;

  @UiThread
  public WebviewActivity_ViewBinding(WebviewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WebviewActivity_ViewBinding(final WebviewActivity target, View source) {
    this.target = target;

    View view;
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
    target.bookLoading = Utils.findRequiredViewAsType(source, R.id.bookloading, "field 'bookLoading'", BookLoading.class);
    target.loaderLayout = Utils.findRequiredViewAsType(source, R.id.loaderLayout, "field 'loaderLayout'", RelativeLayout.class);
    target.errorView = Utils.findRequiredViewAsType(source, R.id.errorView, "field 'errorView'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.continueReadingButton, "method 'onContinueReadingButtonClicked'");
    view2131230783 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContinueReadingButtonClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WebviewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.webView = null;
    target.bookLoading = null;
    target.loaderLayout = null;
    target.errorView = null;

    view2131230783.setOnClickListener(null);
    view2131230783 = null;
  }
}
