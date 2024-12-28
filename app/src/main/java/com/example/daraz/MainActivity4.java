package com.example.daraz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private List<String> imagesUrl;
    final static  String image="https://images.pexels.com/photos/2803229/pexels-photo-2803229.jpeg?auto=compress&cs=tinysrgb&w=600";
    private static final String VIDEO_URL = "https://videos.pexels.com/video-files/29376160/12655176_360_640_60fps.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().load(image).into(imageView);

        VideoView view = findViewById(R.id.videoViews);

        Uri uri = Uri.parse(VIDEO_URL);
        view.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(view);
        view.setMediaController(mediaController);
        view.start();


        Button downloadButton = findViewById(R.id.downloadButton);
        Button vedio = findViewById(R.id.vedio);
        vedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadvideo();
            }
        });

        imagesUrl = new ArrayList<>();
        imagesUrl.add("https://images.pexels.com/photos/15829424/pexels-photo-15829424/free-photo-of-couple-sitting-on-a-bench-and-looking-at-mount-fuji.jpeg?auto=compress&cs=tinysrgb&w=600");
        imagesUrl.add("https://images.pexels.com/photos/3625108/pexels-photo-3625108.jpeg?auto=compress&cs=tinysrgb&w=600");
        imagesUrl.add("https://images.pexels.com/photos/5745817/pexels-photo-5745817.jpeg?auto=compress&cs=tinysrgb&w=600");

       // Picasso.get().load(imageUrl).into(imageView);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Checkpermission()) {
                    downloadAllImages();
                } else {
                    Requestpermission();
                }
            }
        });




    }

    private void downloadvideo(){

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(VIDEO_URL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Downloaded video ");
        request.setDescription("Downloaded vidio using  Downloaded ");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"video.mp4");
        if (downloadManager!=null){
            downloadManager.enqueue(request);
            Toast.makeText(getApplicationContext(), "Video downloaded ", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(), "Video  downloaded Failed ", Toast.LENGTH_SHORT).show();

        }

    }
    private  boolean Checkpermission(){

        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }
    private void Requestpermission(){

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }
    private void downloadAllImages() {
        for (int i = 0; i < imagesUrl.size(); i++) {
            String imageUrls = imagesUrl.get(i);
            downloadImage(imageUrls, i + 1); // Pass index for unique file naming
        }
    }
    private void downloadImage(String url,int index){


        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                try {

                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyImages");
                    if (!directory.exists())directory.mkdir();
                    File file = new File(directory,"image_"+index+".jpg");
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                    out.flush();
                    out.close();
                    Toast.makeText(getApplicationContext(), "Image downloaded to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();


                }catch (IOException e){

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed download images", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                Toast.makeText(getApplicationContext(), "Failed to download image", Toast.LENGTH_SHORT).show();

         }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==PERMISSION_REQUEST_CODE){

            if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                downloadAllImages();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}