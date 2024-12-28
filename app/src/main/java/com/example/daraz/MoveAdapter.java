package com.example.daraz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends BaseAdapter {
    ArrayList<String> moveList;

    Context context;

    public MoveAdapter(ArrayList<String> moveList, Context context) {
        this.moveList = moveList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return moveList.size();
    }

    @Override
    public Object getItem(int i) {
        return moveList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){

            view= LayoutInflater.from(context).inflate(R.layout.newss,viewGroup,false);
        }


        return view;
    }
}
