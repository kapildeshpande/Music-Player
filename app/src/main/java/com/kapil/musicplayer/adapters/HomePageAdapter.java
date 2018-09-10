package com.kapil.musicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kapil.musicplayer.helpers.AudioListHelper;
import com.kapil.musicplayer.R;
import com.kapil.musicplayer.model.Audio;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by kapil on 19-03-2018.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ItemViewHolder> {

    Context context;
    private Listener listener;
    private ArrayList<Audio> favouriteList = null;

    public interface Listener {
        void onClick (int position);
    }

    public void setFavouriteList(ArrayList<Audio> favouriteList) {
        this.favouriteList = favouriteList;
    }

    public void setListener (Listener listener) {
        this.listener = listener;
    }

    public HomePageAdapter(Context context) {
        this.context = context;
    }

    public HomePageAdapter(Context context,ArrayList<Audio> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;
    }

    @Override
    public HomePageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_caption_image,parent,false);

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder,final int position) {
        Audio activeAudio;
        if (favouriteList == null)
            activeAudio = AudioListHelper.audioListHelper.audioArrayList.get(position);
        else {
            activeAudio = favouriteList.get(position);
        }

        CardView cardView = holder.cardView;

        TextView songName = (TextView) cardView.findViewById(R.id.text_view);
        TextView songDuration = (TextView) cardView.findViewById(R.id.duration_text);
        TextView artistName = (TextView) cardView.findViewById(R.id.artist_name);
        final ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);

        String albumArtUri = activeAudio.getAlbumArtUri();
        imageView.setAdjustViewBounds(true);

        if (albumArtUri == null || albumArtUri.equals("")) {
            imageView.setImageDrawable(context.getDrawable(R.mipmap.ic_launcher));
        } else {
            //imageView.setImageURI();
            Glide.with(context).asBitmap().load(activeAudio.getAlbumArtUri())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(imageView);
        }
        //imageView.setImageBitmap(activeAudio.getBitmap());
        songDuration.setText(activeAudio.getSongDuration());
        songName.setText(activeAudio.getTitle());
        artistName.setText(activeAudio.getArtist());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favouriteList == null)
            return AudioListHelper.audioListHelper.audioArrayList.size();
        return favouriteList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ItemViewHolder(CardView itemView) {
            super(itemView);
            this.cardView = itemView;

        }
    }

}