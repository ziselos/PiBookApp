// Generated code from Butter Knife. Do not modify!
package gr.mobile.zisis.pibook.activity.video;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import gr.mobile.zisis.pibook.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoPlayerActivity_ViewBinding implements Unbinder {
  private VideoPlayerActivity target;

  private View view2131230783;

  @UiThread
  public VideoPlayerActivity_ViewBinding(VideoPlayerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VideoPlayerActivity_ViewBinding(final VideoPlayerActivity target, View source) {
    this.target = target;

    View view;
    target.videoPlayer = Utils.findRequiredViewAsType(source, R.id.videoPlayer, "field 'videoPlayer'", YouTubePlayerView.class);
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
    VideoPlayerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoPlayer = null;

    view2131230783.setOnClickListener(null);
    view2131230783 = null;
  }
}
