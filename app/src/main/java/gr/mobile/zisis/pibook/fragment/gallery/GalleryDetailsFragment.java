package gr.mobile.zisis.pibook.fragment.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 3112//17.
 */

public class GalleryDetailsFragment extends Fragment {

    @BindView(R.id.imageTitle)
    AppCompatTextView imageTitle;

    @BindView(R.id.imageCaption)
    AppCompatTextView imageCaption;

    @BindView(R.id.imageImageView)
    AppCompatImageView imageImageView;

    private String TAG = GalleryDetailsFragment.class.getSimpleName();

    private final static String ARGUMENTS_IMAGE_SELECTED = "arguments_image";

    private ArrayList<Image> imageArrayList;
    private ViewPager viewPager;
    private Image image;


    public static  GalleryDetailsFragment newInstance(Image image) {
        Bundle passDataBundle = new Bundle();
        passDataBundle.putParcelable(ARGUMENTS_IMAGE_SELECTED, image);
        GalleryDetailsFragment galleryDetailsFragment = new GalleryDetailsFragment();
        galleryDetailsFragment.setArguments(passDataBundle);
        return galleryDetailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_image_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPassData();
        ButterKnife.bind(this, view);
        initLayout();

    }

    private void initLayout() {
        imageTitle.setText(image.getTitle());
        imageCaption.setText(image.getCaption());
        //emulator
        //String imageLocalhost = image.getImage_url().replace("localhost", "10.0.3.2");
        //device
        String imageLocalhost = image.getImage_url().replace("0.0.0.0", "192.168.1.27");
        Glide.with(getContext())
                .load(imageLocalhost)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageImageView);

    }


    private void getPassData() {
        Bundle getPassDataBundle = this.getArguments();
        if (getPassDataBundle != null) {
            image = getPassDataBundle.getParcelable(ARGUMENTS_IMAGE_SELECTED);
        }
    }
}
