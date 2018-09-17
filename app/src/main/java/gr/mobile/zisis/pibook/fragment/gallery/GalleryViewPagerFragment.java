package gr.mobile.zisis.pibook.fragment.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.adapter.GalleryPagerAdapter;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 3112//17.
 */

public class GalleryViewPagerFragment extends Fragment {

    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;

    private static final String ARGUMENTS_ITEM_POSITION = "arguments_item_position";
    private static final String ARGUMENTS_GALLERY_IMAGES = "arguments_gallery_images";

    public static GalleryViewPagerFragment newInstance(int currentItem, ArrayList<Image> imageArrayList) {
        GalleryViewPagerFragment galleryViewPagerFragment = new GalleryViewPagerFragment();
        Bundle passDataBundle = new Bundle();
        passDataBundle.putInt(ARGUMENTS_ITEM_POSITION, currentItem);
        passDataBundle.putParcelableArrayList(ARGUMENTS_GALLERY_IMAGES, imageArrayList);
        galleryViewPagerFragment.setArguments(passDataBundle);
        return galleryViewPagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_gallery_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getPassData();
        ButterKnife.bind(this, view);
        getPassData();


    }

    private void getPassData() {
        Bundle getPassDataBundle = this.getArguments();
        if (getPassDataBundle != null) {
            int currentItem = getPassDataBundle.getInt(ARGUMENTS_ITEM_POSITION);
            ArrayList<Image> imageArrayList = getPassDataBundle.getParcelableArrayList(ARGUMENTS_GALLERY_IMAGES);
            GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(getChildFragmentManager(), imageArrayList);
            imageViewPager.setAdapter(galleryPagerAdapter);
            imageViewPager.setCurrentItem(currentItem);
        }
    }
}
