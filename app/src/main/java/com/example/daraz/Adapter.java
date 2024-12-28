package com.example.daraz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {


    Context context;
    List<Movie1> movieList;

    public Adapter(Context context, List<Movie1> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        Movie1 id = movieList.get(position);
        holder.textView.setText(id.getName());
        /*holder.text.setText(id.getNumber());*/
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.addd);

                EditText editText = dialog.findViewById(R.id.name_update_item);
                EditText numbor = dialog.findViewById(R.id.numbor);
                TextView textView =dialog.findViewById(R.id.text_i);
                Button button1 = dialog.findViewById(R.id.add);
                textView.setText("update user");
                button1.setText("update user");

                editText.setText((movieList.get(position).getName()));
              /*  numbor.setText((movieList.get(position).getNumber()));*/
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name="",number="";
                        if (!editText.getText().toString().equals("")){
                            name = editText.getText().toString();
                        }else {

                            Toast.makeText(context,"Please enter your name ",Toast.LENGTH_SHORT).show();

                        }
                        if (!numbor.getText().toString().equals("")){
                            number = numbor.getText().toString();
                        }else {

                            Toast.makeText(context,"Please enter your numbor ",Toast.LENGTH_SHORT).show();

                        }

                        movieList.set(position,new Movie1(name,number));
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alart=new AlertDialog.Builder(context)
                        .setTitle("Delete item")
                        .setMessage("you want to delete item ")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                movieList.remove(position);
                                notifyItemRemoved(position);

                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alart.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class  viewholder extends  RecyclerView.ViewHolder {

        TextView textView;
        TextView text;
        LinearLayout linearLayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.title);
            text=itemView.findViewById(R.id.category);
            linearLayout=itemView.findViewById(R.id.linearlayout);
        }
    }
}
