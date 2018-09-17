package gr.mobile.zisis.pibook.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import gr.mobile.zisis.pibook.R;
import gr.mobile.zisis.pibook.callbacks.GalleryItemListener;
import gr.mobile.zisis.pibook.network.parser.images.Image;

/**
 * Created by zisis on 2912//17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private ArrayList<Image> imageArrayList;
    private Context context;
    private GalleryItemListener galleryItemListener;

    public GalleryAdapter(Context context, ArrayList<Image> imageArrayList, GalleryItemListener galleryItemListener) {
        this.imageArrayList = imageArrayList;
        this.context = context;
        this.galleryItemListener = galleryItemListener;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_temp, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.MyViewHolder holder, int position) {
        final Image image = imageArrayList.get(position);

        //String imageUrlLocalhost = image.getImage_url().replace("localhost", "10.0.3.2");
        //emulator
        //String imageThumbUrlLocalhost = image.getImage_thumb_url().replace("localhost", "10.0.3.2");

        //device, replace each time with current ip (for example 192.168.1.35....
        String imageThumbUrlLocalhost = image.getImage_thumb_url().replace("0.0.0.0", "192.168.1.29");

        Glide.with(context).load(imageThumbUrlLocalhost)
                //.override(150, 150)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageThumb);

        holder.imageTextView.setText(image.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (galleryItemListener != null) {
                    galleryItemListener.onGalleryItemClicked(view, holder.getAdapterPosition(), image);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (imageArrayList != null) {
            return imageArrayList.size();
        } else {
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageThumb;
        private AppCompatTextView imageTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageThumb = (AppCompatImageView) itemView.findViewById(R.id.imageTemp);
            imageTextView = (AppCompatTextView)itemView.findViewById(R.id.imageText);
        }
    }
}
