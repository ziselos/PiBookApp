package gr.mobile.zisis.pibook.fragment.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.adapter.GalleryAdapter;
import gr.mobile.zisis.pibook.callbacks.GalleryItemListener;
import gr.mobile.zisis.pibook.fragment.gallery.GalleryViewPagerFragment;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.images.Image;
import gr.mobile.zisis.pibook.network.parser.images.ImageResponse;
import gr.mobile.zisis.pibook.utils.AutoFitGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zisis on 3112//17.
 */

public class GalleryFragment extends Fragment implements GalleryItemListener {

    @BindView(R.id.imageRecyclerView)
    RecyclerView imageRecyclerView;

    private final static String TAG = GalleryFragment.class.getSimpleName();

    private ArrayList<Image> imageArrayList;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;


    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_image_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fetchImages();


    }

    private void fetchImages() {
        // pDialog.setMessage("Downloading images from PiBook...");
        // pDialog.show();

        PiBookService piBookService = PiBookClient.getApiService();
        //call galleryApi.json
        Call<ImageResponse> call = piBookService.getPiBookImages();

        //enque Callback will be call when get response
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.toString());
                    //pDialog.hide();
                    imageArrayList = response.body().getImages();
                    setImageRecyclerViewAdapter(imageArrayList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                // pDialog.hide();
            }
        });


    }

    private void setImageRecyclerViewAdapter(ArrayList<Image> list) {
        mAdapter = new GalleryAdapter(getContext(), list, this);

        AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(getContext(), 500);
        imageRecyclerView.setLayoutManager(autoFitGridLayoutManager);
        imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onGalleryItemClicked(View sharedView, int adapterPosition, Image image) {
        Fragment galleryViewPagerFragment = GalleryViewPagerFragment.newInstance(adapterPosition, imageArrayList);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(TAG)
                .replace(R.id.content, galleryViewPagerFragment)
                .commit();

    }
}
