package com.great.unifythelit_firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

import java.util.ArrayList;

/*import com.google.android.exoplayer2.ExoPlayerFactory;*/

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<ModelClass> arrayList;
    /*ArrayList<ModelClassVideo> classVideos;*/
    Context context;


    public RecyclerAdapter(ArrayList<ModelClass> arrayList,/* ArrayList<ModelClassVideo> classVideos,*/Context context) {
        this.arrayList = arrayList;
      /*  this.classVideos = classVideos;*/
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context)
                .load(arrayList.get(position).getPictureLink())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        VideoView videoView;
        /*PlayerView exoPlayerView;*//*
        SimpleExoPlayer simpleExoPlayer;*/
        ProgressDialog progDailog;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    /*Toast.makeText(context, arrayList.get(pos).getMetaData(), Toast.LENGTH_SHORT).show();*/
                    String uri = arrayList.get(pos).getPictureLink();
                    Toast.makeText(context, "Your Video is Being Played", Toast.LENGTH_SHORT).show();
                    setVideo(uri);
                    progDailog = ProgressDialog.show(context, "Please wait ...", "Loading Your Video", true);
                    progDailog.setCanceledOnTouchOutside(true);
                }
            });
        }

        public void setVideo(String url) {
            try {

                MediaPlayer mediaPlayer=new MediaPlayer();
                mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
                MediaController mediaController = new MediaController(context);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(url));
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Toast.makeText(context, "Playing...", Toast.LENGTH_SHORT).show();
                        progDailog.dismiss();
                    }
                });

                /*Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

                *//*BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter.Builder(context).build();

                ExoTrackSelection.Factory factories=new AdaptiveTrackSelection.Factory();
                TrackSelector trackSelector = new AdaptiveTrackSelection.Factory(bandwidthMeter);

                new SimpleExoPlayer.Builder(context).build().getTrackSelector().onSelectionActivated(trackSelector);*//*
                *//*Uri videoURL = arrayList.get(position).getPictureLink();*//*
                Uri videouri = Uri.parse(url);

                DefaultHttpDataSource.Factory factory = new DefaultHttpDataSource.Factory().setUserAgent("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaItem mediaItem=MediaItem.fromUri(videouri);
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory, extractorsFactory).createMediaSource(mediaItem);
                exoPlayerView.setPlayer(simpleExoPlayer);
                simpleExoPlayer.setMediaSource(mediaSource);
                simpleExoPlayer.prepare();
                simpleExoPlayer.setPlayWhenReady(true);
                Toast.makeText(context, videouri.toString(), Toast.LENGTH_SHORT).show();*/

            } catch (Exception ex) {
                Log.d("Exoplayer Crashed", ex.getMessage());
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
