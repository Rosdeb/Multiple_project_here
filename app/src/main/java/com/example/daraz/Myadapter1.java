package com.example.daraz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Myadapter1 extends RecyclerView.Adapter<Myadapter1.Viewholder> {
    Context context;
    List<Movie>movieList;
    int lastposition=-1;

    public Myadapter1(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.top_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Movie movie=movieList.get(position);

        holder.price.setText(movie.getPrice().toString());
        holder.title.setText(movie.getTitle());
        holder.category.setText(movie.getCategory());
        Glide.with(context).load(movie.getImage()).into(holder.imageView);
        animations(holder.itemView,position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title" , movie.getTitle());
                bundle.putString("category" , movie.getCategory());
                bundle.putString("image" , movie.getImage());
                bundle.putDouble("price" , movie.getPrice());
                bundle.putString("description", movie.getDescription());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{

        ImageView imageView;

        TextView title;

        TextView category;
        TextView price;

        LinearLayout linearLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.category);
            linearLayout=itemView.findViewById(R.id.linearlayout);




        }
    }
    private  void animations(View view,int position){
        if (position>lastposition) {
            lastposition=position;
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            view.startAnimation(animation);


        }

    }
}


