package com.example.daraz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Gainmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    TextView textView;
    private int quantity=1;
    Button button,convert;
    Button button_sheet;
    SwipeRefreshLayout refreshLayout;
    int pageweidth =700;

    int pageheight =700;

    final static  String image="https://plus.unsplash.com/premium_photo-1680087014917-904bb37c5191?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8ZnJlZXxlbnwwfHwwfHx8MA%3D%3D";

    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        button_sheet=findViewById(R.id.buttom_sheet);
        button_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
/*
        textView=findViewById(R.id.textview);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        button = findViewById(R.id.button);
        refreshLayout = findViewById(R.id.refress);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        restartApp();
                        Toast.makeText(getApplicationContext(),"Refress",Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        convert = findViewById(R.id.pdf);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                }else {
             //       createPdf();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (Checkpermission()){
               //     dowloadImage();

             //   }else {
             //       Requestpermission();
              //  }
            }
        });




        arrayList = new ArrayList<>();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {

          //  getContact();
        }

    }

    private void createPdf() {


        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageweidth,pageheight,1).create();
        PdfDocument.Page page =pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(15);

        canvas.drawText("hello my name is rosdeb  koch Iam study in diploma in dhaka saver ashulia in ghusbag sonia gate ",pageheight/2,pageheight/2,paint);
        pdfDocument.finishPage(page);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/rosdebkoch.pdf");
       try {
           pdfDocument.writeTo(new FileOutputStream(file));
           Toast.makeText(getApplicationContext(),"download successfully",Toast.LENGTH_SHORT).show();


       }catch (IOException e){
           e.printStackTrace();
       }

    }

    private void dowloadImage() {

        Picasso.get().load(image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                try {
                    File directory= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Rosdeb");
                    if (!directory.exists())directory.mkdir();
                    File file = new File(directory,"image.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();


                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {


            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    private  boolean Checkpermission(){

        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
    }
 private void    Requestpermission(){
     ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
 }
    private void getContact(){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //now add
            arrayList.add(name+"\n"+mobile);
            textView.setText(arrayList.toString());

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
        if (requestCode==10){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dowloadImage();
            }
        }
        if (requestCode==100){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                createPdf();
            }
        }
    }

    public void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity5.class); // Replace MainActivity with your launcher activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Close the current activity
        System.exit(0); // Forcefully terminate the app process
    }
    //below just restartapp in my won android studio project

    public void restartapp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity5.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
    }
    private void show(){

        final  Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_sheet);

        ImageButton add= dialog.findViewById(R.id.add);
        ImageButton nef=dialog.findViewById(R.id.mainas);
        TextView item=dialog.findViewById(R.id.item);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity>1){
                    quantity--;
                    item.setText(String.valueOf(quantity));
                }
            }
        });
        nef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                item.setText(String.valueOf(quantity));
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.Animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }

}