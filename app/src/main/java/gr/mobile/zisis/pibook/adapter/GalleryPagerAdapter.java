package gr.mobile.zisis.pibook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import gr.mobile.zisis.pibook.fragment.gallery.GalleryDetailsFragment;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 3112//17.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Image> imageArrayList;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<Image> imageArrayList) {
        super(fm);
        this.imageArrayList = imageArrayList;
    }

    @Override
    public Fragment getItem(int position) {
       Image image = imageArrayList.get(position);
       return GalleryDetailsFragment.newInstance(image);
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }
}
