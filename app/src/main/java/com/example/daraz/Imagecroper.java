package com.example.daraz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class Imagecroper extends AppCompatActivity {

    String result;
    Uri fileuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagecroper);

        show();
        String dest_uri=new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop.Options options=new UCrop.Options();
        UCrop.of(fileuri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(Imagecroper.this);
    }
    private void show(){

        Intent intent = getIntent();

        if (intent.getExtras()!=null){

            result=intent.getStringExtra("data");
            fileuri=Uri.parse(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&resultCode==100){

            final  Uri resulturi=UCrop.getOutput(data);
            Intent intent = new Intent();
            intent.putExtra("RESULT",resulturi+"");
            setResult(-1,intent);
            finish();
        } else if (requestCode==UCrop.RESULT_ERROR) {
         final  Throwable cropError=UCrop.getError(data);
        }
    }
}