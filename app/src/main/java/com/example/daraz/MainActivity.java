package com.example.daraz;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import kotlinx.coroutines.scheduling.Task;

public class MainActivity extends AppCompatActivity {

    private ImageView scanner,camara ;
    ImageSlider imageSlider;


    Myadapter1 myadapter1;
    List<Movie>movieList;
    RequestQueue requestQueue;
    ScrollView scrollView;

    ArrayList<SlideModel> slideModels;
    RecyclerView recyclerViewtop;
    LinearLayout search;
    ImageButton imageButton;
    SwipeRefreshLayout swipeRefreshLayout;

    int color = Color.parseColor("#BFBABA");
    int REQUEST_CODE=101;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//connection
        connectioncheck();

        //initial
        scanner=findViewById(R.id.scanner);
        scrollView=findViewById(R.id.scrollviewid);
    /*    swipeRefreshLayout=findViewById(R.id.swipeing);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retstart();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_SHORT).show();

                    }
                },1000);
            }
        });*/


        search=findViewById(R.id.searchviewid);
        imageSlider=findViewById(R.id.image_slider);
        imageButton=findViewById(R.id.buttom);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });
        recyclerViewtop=findViewById(R.id.recycleviewtop);


        recyclerViewtop.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        recyclerViewtop.setNestedScrollingEnabled(false);
        recyclerViewtop.setLayoutManager(layoutManager);
        requestQueue = Vollysingleton.getInstance(this).getRequestQueue();

        movieList=new ArrayList<>();
        load();


        imageSlider.setSlideAnimation(AnimationTypes.CUBE_IN);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }

            @Override
            public void doubleClick(int i) {

            }
        });
        slideModels=new ArrayList<SlideModel>();
        slideModels.add(new SlideModel(R.drawable.car1,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.car2,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.car3,ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setBackgroundColor(Color.parseColor("#BFBABA"));
                Toast.makeText(getApplicationContext(),"successfully",Toast.LENGTH_SHORT).show();

            }
        });
        camara=findViewById(R.id.camaraid);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = new Intent(MediaStore.MEDIA_SCANNER_VOLUME);
                IntentIntegrator intentIntegrator= new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("Scan a QR code");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.initiateScan();
            }
        });



    }

    private void retstart() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            Toast.makeText(getApplicationContext(),"successfully",Toast.LENGTH_SHORT).show();
        }
        if (intentResult!=null){

            String contents= intentResult.getContents();
            if (contents!=null){

                Toast.makeText(getApplicationContext(),intentResult.getContents(),Toast.LENGTH_SHORT).show();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    private void  load(){
        String URL="https://fakestoreapi.com/products";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){


                    try {
                        JSONObject jsonObject =response.getJSONObject(i);
                        Double price = jsonObject.getDouble("price");
                        String title = jsonObject.getString("title");
                        String category = jsonObject.getString("category");
                        String image = jsonObject.getString("image");
                        String description = jsonObject.getString("description");

                        Movie movie =new Movie(image,category,description,title,price);
                        movieList.add(movie);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                }
                myadapter1=new Myadapter1(MainActivity.this,movieList);
                recyclerViewtop.setAdapter(myadapter1);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void connectioncheck(){

        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= manager.getActiveNetworkInfo();
        if (networkInfo!=null) {
           if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){

           }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){

            }
        }else {

            Toast.makeText(getApplicationContext(),"Internet not connection",Toast.LENGTH_SHORT).show();
        }
        

    }



}