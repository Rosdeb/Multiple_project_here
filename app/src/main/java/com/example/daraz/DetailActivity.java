package com.example.daraz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title,category,description,price;
    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;
    private static final int PERMISSION_REQUEST_CODE = 1;



    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView=findViewById(R.id.image_view);
        price=findViewById(R.id.priceview);
       title=findViewById(R.id.titleview);
       category=findViewById(R.id.categoryview);
       description=findViewById(R.id.movervie_des);

       Bundle bundle = getIntent().getExtras();
       Double price1= bundle.getDouble("price");
       String image1=bundle.getString("image");
       String title1=bundle.getString("title");
       String description1=bundle.getString("description");
       String category1=bundle.getString("category");


        Glide.with(this).load(image1).into(imageView);
        title.setText(description1);
        category.setText(category1);
        price.setText(Double.toString(price1));
        description.setText(title1);




        /*bitmapDrawable= (BitmapDrawable) imageView.getDrawable();if (bitmapDrawable != null && bitmapDrawable instanceof BitmapDrawable) {
             bitmap = bitmapDrawable.getBitmap();
            // Use the bitmap as needed
        } else {
            // Handle the case when the drawable is null or not a BitmapDrawable
            Log.e("DetailActivity", "Drawable is null or not a BitmapDrawable");
        }


        FileOutputStream fileOutputStream =null;
        File sdcar= Environment.getExternalStorageDirectory();
        File directory=new File(sdcar.getAbsoluteFile()+"/Download");
        directory.mkdir();
        String filename=String.format("%d,jpg",System.currentTimeMillis());
        File outputfile=new File(directory,filename);

        Toast.makeText(getApplicationContext(),"image dwonload sucessfully",Toast.LENGTH_SHORT).show();
        try {

             if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                 Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                 intent.setData(Uri.fromFile(outputfile));
                 sendBroadcast(intent);
            } else {
                Log.e("DetailActivity", "Bitmap is null, cannot compress!");
            }


        }catch (FileNotFoundException e){

            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }*/



    }
    public void checkPermission(){

        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.Q&&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

    }else {
            saveImageindevice();

        }
    }

    public void saveImageindevice(){


        String imageUrl = "https://images.pexels.com/photos/1324803/pexels-photo-1324803.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        saveImage(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void saveImage(Bitmap bitmap){
        String filename="Download"+System.currentTimeMillis()+".jpg";
        File directory=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!directory.exists()){

            directory.mkdir();
        }
        File imagefile=new File(directory,filename);
        try(FileOutputStream fos =new FileOutputStream(imagefile)) {

            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } else {
                Log.e("DetailActivity", "Bitmap is null, cannot compress!");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();

        }


    }

    private void connectivycheck(){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo!=null){

            if (networkInfo.getType()==ConnectivityManager.TYPE_WIFI){


            }if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){


            }else {
                Toast.makeText(getApplicationContext(),"Please connect to internet",Toast.LENGTH_SHORT).show();
            }

        }
    }
}