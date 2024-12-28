package com.example.daraz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.Viewholder> {


    private String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4",
            "Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4","Item 4"};

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public static class  Viewholder extends  RecyclerView.ViewHolder {
        TextView textView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.title);

        }
    }
}
