package gr.mobile.zisis.pibook.activity.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.fragment.gallery.GalleryFragment;

/**
 * Created by zisis on 3112//17.
 */

public class GalleryToViewPagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTextView)
    AppCompatTextView toolbarTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gallery_home);
        initToolbar(toolbar);
        loadImageGallery();
    }

    @Override
    public void initLayout() {
        toolbarTextView.setText(getResources().getString(R.string.toolbar_gallery_title_text));

    }

    private void loadImageGallery() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, GalleryFragment.newInstance())
                .commitAllowingStateLoss();
    }
}
