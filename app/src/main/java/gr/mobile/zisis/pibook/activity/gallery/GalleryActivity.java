package gr.mobile.zisis.pibook.activity.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.activity.base.BaseActivity;
import gr.mobile.zisis.pibook.adapter.GalleryAdapter;
import gr.mobile.zisis.pibook.callbacks.GalleryItemListener;
import gr.mobile.zisis.pibook.fragment.gallery.GalleryDetailsFragment;
import gr.mobile.zisis.pibook.network.api.PiBookService;
import gr.mobile.zisis.pibook.network.client.PiBookClient;
import gr.mobile.zisis.pibook.network.parser.images.Image;
import gr.mobile.zisis.pibook.network.parser.images.ImageResponse;
import gr.mobile.zisis.pibook.utils.AutoFitGridLayoutManager;
import gr.mobile.zisis.pibook.utils.GridSpacingDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zisis on 2912//17.
 */

public class GalleryActivity extends BaseActivity implements GalleryItemListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTextView)
    AppCompatTextView toolbarTextView;

    @BindView(R.id.imageRecyclerView)
    RecyclerView imageRecyclerView;

    private String TAG = GalleryActivity.class.getSimpleName();
    private ArrayList<Image> imageArrayList;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;

    private final static int columnsGrid = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_gallery);
        initToolbar(toolbar);
        fetchImages();
        //setImageRecyclerViewAdapter();

    }

    @Override
    public void initLayout() {
        toolbarTextView.setText(getResources().getString(R.string.toolbar_gallery_title_text));
    }


    private void setImageRecyclerViewAdapter(ArrayList<Image> list) {
        pDialog = new ProgressDialog(this);
        mAdapter = new GalleryAdapter(getApplicationContext(), list, this);

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), columnsGrid);
        AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(getApplicationContext(), 500);
        //imageRecyclerView.addItemDecoration(new GridSpacingDecoration(2, dpToPx(10), true));
        imageRecyclerView.setLayoutManager(autoFitGridLayoutManager);
        imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setAdapter(mAdapter);

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
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
               // pDialog.hide();
            }
        });


    }


    @Override
    public void onGalleryItemClicked(View sharedView, int adapterPosition, Image image) {
        GalleryDetailsFragment fragment = GalleryDetailsFragment.newInstance(image);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }
}
