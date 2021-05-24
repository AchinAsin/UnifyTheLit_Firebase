package com.great.unifythelit_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowData extends AppCompatActivity {

    ArrayList<ModelClass> arrayList;
    /*ArrayList<ModelClassVideo> classVideos;*/
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    DatabaseReference referenceImage,referenceVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView=findViewById(R.id.recyclerView);

        referenceImage = FirebaseDatabase.getInstance().getReference().child("Pictures");
        /*referenceVideo = FirebaseDatabase.getInstance().getReference().child("Videos");*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        /*classVideos=new ArrayList<>();*/
        init();

    }

    private void init() {
        clearAll();
        referenceImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelClass images = new ModelClass();
                    images.setPictureLink(snapshot.child("PictureLink").getValue().toString());
                    images.setMetaData(snapshot.child("MetaData").getValue().toString());
                    arrayList.add(images);
                    /*Toast.makeText(ShowData.this, images.getPictureLink(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ShowData.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
                    Log.d("ImageLink",images.getPictureLink());
                    Log.d("VideoLink",images.getVideoLink());*/
                }
                recyclerAdapter=new RecyclerAdapter(arrayList,ShowData.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                /*recyclerAdapter.setItemClickedListener(PortraitActivity.this);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*referenceVideo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    *//*ModelClassVideo videos=new ModelClassVideo();*//*
                    ModelClass videos=new ModelClass();
                    videos.setVideoLink(snapshot1.child("VideosLink").getValue().toString());
                    videos.setMetaData(snapshot1.child("MetaData").getValue().toString());
                    arrayList.add(videos);
                    Toast.makeText(ShowData.this, videos.getVideoLink(), Toast.LENGTH_SHORT).show();
                    Log.d("VideoLink",videos.getVideoLink());
                }
                recyclerAdapter=new RecyclerAdapter(arrayList,ShowData.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    private void clearAll() {
        if (arrayList != null) {
            arrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        arrayList = new ArrayList<>();
    }
}