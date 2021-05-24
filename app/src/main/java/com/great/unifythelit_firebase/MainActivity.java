package com.great.unifythelit_firebase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    TextView textViewPhoto, textViewVideo;
    Button buttonSelectImage, buttonSelectVideo, buttonUpload, buttonShowData, buttonUploadImages, buttonUploadVideos;
    ArrayList<Uri> ImageList = new ArrayList<>();
    ArrayList<Uri> VideoList = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int uploadcount = 0;
    private ProgressBar mProgressBar;
    private Uri ImageUri;
    private Uri VideoUri;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth.signInAnonymously();
        textViewPhoto = findViewById(R.id.textView_Image);
        textViewVideo = findViewById(R.id.textView_Video);
        buttonSelectImage = findViewById(R.id.button_chooseImage);
        buttonSelectVideo = findViewById(R.id.button_chooseVideo);
        buttonUpload = findViewById(R.id.button_upload);
        buttonShowData = findViewById(R.id.button_showData);
        /*buttonUploadImages=findViewById(R.id.button_uploadImage);
        buttonUploadVideos=findViewById(R.id.button_uploadVideo);*/
        mProgressBar = findViewById(R.id.progressBar2);
        textViewPhoto.setVisibility(View.INVISIBLE);
        textViewVideo.setVisibility(View.INVISIBLE);

        /*buttonUploadImages.setVisibility(View.INVISIBLE);
        buttonUploadVideos.setVisibility(View.INVISIBLE);*/
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                /*UploadVideo();*/
            }
        });
        buttonShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowData.class));
            }
        });

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please Select More than 1 Images", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                /*intent.setType("image/*");*/
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);

                /*buttonUploadImages.setVisibility(View.VISIBLE);*/
            }
        });

        buttonSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please Select More than 1 Videos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                /*intent.setType("image/* video/*");*/
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_VIDEO);
                /*buttonUploadVideos.setVisibility(View.VISIBLE);*/
            }
        });

        /*buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("UploadFolder");
                for (uploadcount = 0; uploadcount < ImageList.size(); uploadcount++) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Uri IndividualImage = ImageList.get(uploadcount);

                    String mimeType = getContentResolver().getType(IndividualImage);
                    Cursor returnCursor = getContentResolver().query(IndividualImage, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    String fileName = returnCursor.getString(nameIndex);
                    final StorageReference ImageName = ImageFolder.child(fileName);

                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mProgressBar.setProgress(0);
                                    String url = String.valueOf(uri);
                                    String fileName = ImageName.toString();
                                    String metaData = taskSnapshot.getMetadata().getName();
                                    Toast.makeText(MainActivity.this, metaData, Toast.LENGTH_SHORT).show();
                                    StoreLink(url, fileName, metaData);
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }

            }
        });*/

        /*buttonUploadVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("UploadFolder");
                for (uploadcount = 0; uploadcount < VideoList.size(); uploadcount++) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Uri IndividualImage = ImageList.get(uploadcount);

                    String mimeType = getContentResolver().getType(IndividualImage);
                    Cursor returnCursor = getContentResolver().query(IndividualImage, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    String fileName = returnCursor.getString(nameIndex);
                    final StorageReference ImageName = ImageFolder.child(fileName);

                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mProgressBar.setProgress(0);
                                    String url = String.valueOf(uri);
                                    String fileName = ImageName.toString();
                                    String metaData = taskSnapshot.getMetadata().getName();
                                    Toast.makeText(MainActivity.this, metaData, Toast.LENGTH_SHORT).show();
                                    VideoLink(url, fileName, metaData);
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }
            }
        });*/
    }

    /*private void VideoLink(String url, String fileName, String metaData) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Videos");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("VideoLink", url);
        hashMap.put("FileName", fileName);
        hashMap.put("MetaData", metaData);
        databaseReference.push().setValue(hashMap);
        String imageSelected = "Videos Uploaded";
        textViewVideo.setText(imageSelected);
        buttonSelectVideo.setVisibility(View.GONE);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            {
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int countClipData = data.getClipData().getItemCount();

                        int currentImageSelect = 0;
                        while (currentImageSelect < countClipData) {
                            ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                            ImageList.add(ImageUri);
                            String imageSelected = "Images Selected";
                            textViewPhoto.setText(imageSelected);
                            textViewPhoto.setVisibility(View.VISIBLE);
                            currentImageSelect = currentImageSelect + 1;
                            buttonSelectImage.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(this, "Error Choosing Multiple Images", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        } else if (requestCode == PICK_VIDEO) {
            {
                if (resultCode == RESULT_OK) {
                    {
                        if (data.getClipData() != null) {
                            int countClipData = data.getClipData().getItemCount();
                            int currentVideoSelect = 0;
                            while (currentVideoSelect < countClipData) {
                                VideoUri = data.getClipData().getItemAt(currentVideoSelect).getUri();
                                ImageList.add(VideoUri);
                                String imageSelected = "Videos Selected";
                                textViewVideo.setText(imageSelected);
                                textViewVideo.setVisibility(View.VISIBLE);
                                currentVideoSelect = currentVideoSelect + 1;
                                buttonSelectVideo.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(this, "Error Choosing Multiple Videos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void UploadVideo() {
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("UploadFolder");
        for (uploadcount = 0; uploadcount < ImageList.size(); uploadcount++) {
            mProgressBar.setVisibility(View.VISIBLE);
            Uri IndividualImage = ImageList.get(uploadcount);

            String mimeType = getContentResolver().getType(IndividualImage);
            Cursor returnCursor = getContentResolver().query(IndividualImage, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String fileName = returnCursor.getString(nameIndex);
            final StorageReference ImageName = ImageFolder.child(fileName);

            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mProgressBar.setProgress(0);
                            String url = String.valueOf(uri);
                            String fileName = ImageName.toString();
                            String metaData = taskSnapshot.getMetadata().getName();
                            Toast.makeText(MainActivity.this, metaData, Toast.LENGTH_SHORT).show();
                            StoreLink(url, fileName, metaData);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
    }

    /*private void VideoLink(String url, String fileName, String metaData) {
        String photographyLocation = getIntent().getStringExtra("SelectedCategory");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Videos");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("VideoLink", url);
        hashMap.put("FileName", fileName);
        hashMap.put("MetaData", metaData);
        databaseReference.push().setValue(hashMap);
        String imageSelected = "Images Uploaded";
        textViewPhoto.setText(imageSelected);
        String VideoUploaded = "Videos Uploaded";
        textViewVideo.setText(VideoUploaded);
        buttonSelectImage.setVisibility(View.GONE);
        buttonUpload.setVisibility(View.GONE);
    }*/

    public void UploadImage()
    {
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("UploadFolder");
        for (uploadcount = 0; uploadcount < ImageList.size(); uploadcount++) {
            mProgressBar.setVisibility(View.VISIBLE);
            Uri IndividualImage = ImageList.get(uploadcount);

            String mimeType = getContentResolver().getType(IndividualImage);
            Cursor returnCursor = getContentResolver().query(IndividualImage, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String fileName = returnCursor.getString(nameIndex);
            final StorageReference ImageName = ImageFolder.child(fileName);

            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mProgressBar.setProgress(0);
                            String url = String.valueOf(uri);
                            String fileName = ImageName.toString();
                            String metaData = taskSnapshot.getMetadata().getName();
                            Toast.makeText(MainActivity.this, metaData, Toast.LENGTH_SHORT).show();
                            StoreLink(url, fileName, metaData);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
    }
    private void StoreLink(String url, String fileName, String metaData) {
        /*String photographyLocation = getIntent().getStringExtra("SelectedCategory");*/
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Pictures");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("PictureLink", url);
        hashMap.put("FileName", fileName);
        hashMap.put("MetaData", metaData);
        databaseReference.push().setValue(hashMap);
        String imageSelected = "Images Uploaded";
        textViewPhoto.setText(imageSelected);
        String VideoUploaded = "Videos Uploaded";
        textViewVideo.setText(VideoUploaded);
        buttonSelectImage.setVisibility(View.GONE);
        buttonUpload.setVisibility(View.GONE);
    }

}
