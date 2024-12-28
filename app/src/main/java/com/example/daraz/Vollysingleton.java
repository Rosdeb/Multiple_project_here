package com.example.daraz;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Vollysingleton {
    private RequestQueue requestQueue;
    private static Vollysingleton mInstance;
    private Vollysingleton(Context context){

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

   public static synchronized Vollysingleton getInstance(Context context) {

        if (mInstance==null){

            mInstance=new Vollysingleton(context);

        }return  mInstance;

   }public RequestQueue getRequestQueue(){return requestQueue;}
}
