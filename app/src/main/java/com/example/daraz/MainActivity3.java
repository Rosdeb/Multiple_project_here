package com.example.daraz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowId;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    List<Movie1>movie1List;
    Adapter adapter;
    RecyclerView recyclerView;
    MaterialButton add_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView=findViewById(R.id.recycleviews);
        add_item=findViewById(R.id.add_item);

        movie1List=new ArrayList<>();






        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainActivity3.this);
                dialog.setContentView(R.layout.addd);

                EditText editText = dialog.findViewById(R.id.name_update_item);
                EditText numbor = dialog.findViewById(R.id.numbor);

                Button button1 = dialog.findViewById(R.id.add);


                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name="",number="";
                        if (!editText.getText().toString().equals("")){
                            name = editText.getText().toString();
                        }else {

                            Toast.makeText(getApplicationContext(),"Please enter your name ",Toast.LENGTH_SHORT).show();

                        }
                        if (!numbor.getText().toString().equals("")){
                            number = numbor.getText().toString();
                        }else {

                            Toast.makeText(getApplicationContext(),"Please enter your numbor ",Toast.LENGTH_SHORT).show();

                        }
                        movie1List.add(new Movie1(name,number));
                        adapter.notifyItemInserted(movie1List.size()-1);
                        recyclerView.scrollToPosition(movie1List.size()-1);
                        dialog.dismiss();


                    }

                });

                dialog.show();


            }
        });




        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));
        movie1List.add(new Movie1("rosdeb","0124574444"));



        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(this,movie1List);
        recyclerView.setAdapter(adapter);





    }
}