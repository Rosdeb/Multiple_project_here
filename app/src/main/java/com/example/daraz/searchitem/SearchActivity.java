package com.example.daraz.searchitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daraz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private userAdapter userAdapter;

    private Handler handler = new Handler(Looper.getMainLooper());
    private  Runnable  searchRunnable;

    private List<User> userList;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerViewss);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        userList = new ArrayList<>();
        userAdapter = new userAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

        requestQueue = Volley.newRequestQueue(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fatchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                // Delay the search execution
                searchRunnable = () -> fatchData(newText);
                handler.postDelayed(searchRunnable, 100); // 500ms debounce time
                return true;

            }
        });
    }

    private void fatchData(String query) {
        if (query.isEmpty()) {
            userList.clear();
            userAdapter.notifyDataSetChanged();
            return;
        }
        String url = "https://api.github.com/search/users?q=" + query; // Replace with your API endpoint

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        userList.clear();
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject userObj = items.getJSONObject(i);
                                String login = userObj.getString("login");
                                String avatarUrl = userObj.getString("avatar_url");
                                String htmlUrl = userObj.getString("html_url");
                                userList.add(new User(login, avatarUrl,htmlUrl));
                            }
                            userAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);
    }


}