package com.example.daraz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import kotlinx.coroutines.scheduling.Task;

public class MainActivity7 extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);


        textView=findViewById(R.id.showdata);
        imageView=findViewById(R.id.scan);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });
        swipeRefreshLayout=findViewById(R.id.showdatasca);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restartapp();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_SHORT).show();

                    }
                },1000);
            }
        });


//
    }
    public void restartapp(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult!=null){
            String content= intentResult.getContents();
            if (content!=null){
                textView.setText(content);
               // Toast.makeText(getApplicationContext(),"Information:"+content,Toast.LENGTH_LONG).show();
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void scan(){

        IntentIntegrator intentIntegrator= new IntentIntegrator(MainActivity7.this);
        intentIntegrator.setPrompt("Scan a QR code");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();


    }
}