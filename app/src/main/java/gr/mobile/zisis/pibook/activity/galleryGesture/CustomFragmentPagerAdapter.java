package gr.mobile.zisis.pibook.activity.galleryGesture;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import gr.mobile.zisis.pibook.network.parser.images.Image;


/**
 * Created by zisis on 81//18.
 */

public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Image> imageArrayList;

    public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Image> imageArrayList) {
        super(fm);
        this.imageArrayList = imageArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        Image image = imageArrayList.get(position);
        return ImageGalleryFragment.newInstance(image);
    }

    @Override
    public int getCount() {
        if (imageArrayList.size() > 0) {
            return imageArrayList.size();
        } else {
            return 0;
        }
    }
}
