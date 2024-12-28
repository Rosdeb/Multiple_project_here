package com.example.daraz;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    ImageView imageView;

    ActivityResultLauncher<String> mncontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


imageView=findViewById(R.id.imageview);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mncontent.launch("image/*");
    }
});



        mncontent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(MainActivity2.this,Imagecroper.class);
                intent.putExtra("data",result.toString());
                startActivityForResult(intent,100);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==-1&&requestCode==100){

            String result=data.getStringExtra("RESULT");
            Uri resulturi=null;
            if (result!=null){

                resulturi=Uri.parse(result);
            }
            imageView.setImageURI(resulturi);
        }


    }
}