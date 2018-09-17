package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.common.Definitions;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 81//18.
 */

public class ImageGalleryFragment extends Fragment {

    @BindView(R.id.displayImageView)
    AppCompatImageView mImageView;

    private final static String ARGUMENTS_IMAGE = "image";

    private ArrayList<Image> imageArrayList;

    private int indexNumber;

    private Image image;

    public static ImageGalleryFragment newInstance(Image image) {
        ImageGalleryFragment imageGalleryFragment = new ImageGalleryFragment();
        Bundle passDataBundle = new Bundle();
        passDataBundle.putParcelable(ARGUMENTS_IMAGE, image);
        imageGalleryFragment.setArguments(passDataBundle);
        return imageGalleryFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPassData();
        ButterKnife.bind(this, view);
        initLayout();
    }

    private void initLayout() {
        String imageLocalhost = image.getImage_url().replace(Definitions.REPLACE_TARGET, Definitions.REPLACE_SOURCE);

        Glide.with(getContext()).load(imageLocalhost)
                .fitCenter()
                .centerCrop()
                .into(mImageView);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_display, container, false);
    }
    //    @Nullable


    private void getPassData() {
        Bundle getPassDataBundle = this.getArguments();
        if (getPassDataBundle != null) {
            image = getPassDataBundle.getParcelable(ARGUMENTS_IMAGE);
        }
    }
}
