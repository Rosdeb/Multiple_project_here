package com.example.daraz;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class second {

    List<Movie1> movielist;

    String url="https://fakestoreapi.com/products";
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            for (int j=0;j<response.length();j++){

                try {
                    JSONObject jsonObject = response.getJSONObject(j);
                    String name=jsonObject.getString("title");
                    String images=jsonObject.getString("image");
                   /* Movie1 movie1=new Movie1(name,images);
*/
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

}
