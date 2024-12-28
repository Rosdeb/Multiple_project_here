package com.example.daraz.message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daraz.MainActivity8;
import com.example.daraz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity12 extends AppCompatActivity {

    MessageAdapter adapter;
    private WebSocket webSocket;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        ImageView send = findViewById(R.id.send1);
        final EditText messagebox=findViewById(R.id.messagebox1);
        ListView messagelist=findViewById(R.id.messagelist1);

        instantiateWebSocket();
        adapter=new MessageAdapter();
        messagelist.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message= messagebox.getText().toString();
                if (!message.isEmpty()){
                    webSocket.send(message);
                    messagebox.setText("");
                    JSONObject jsonObject = new JSONObject();


                    try {
                    jsonObject.put("message",message);
                    jsonObject.put("byServer",false);
                    adapter.addItem(jsonObject);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void instantiateWebSocket() {


        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.31.96:8080").build();
        SocketListener socketListener = new SocketListener(this);
        webSocket = client.newWebSocket(request, socketListener);



    }
    public class SocketListener extends WebSocketListener {


        public MainActivity12 activity;



        public SocketListener(MainActivity12 activity) {
            this.activity = activity;
        }



        @Override
        public void onOpen(WebSocket webSocket, Response response) {


            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    Toast.makeText(activity, "Connection Established!", Toast.LENGTH_LONG).show();

                }

            });

        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject jsonObject = new JSONObject();

                    try {


                        jsonObject.put("message", text);
                        jsonObject.put("byServer", true);

                        adapter.addItem(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }



        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }



        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }



        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }



        @Override
        public void onFailure(WebSocket webSocket, final Throwable t, @Nullable final Response response) {
            super.onFailure(webSocket, t, response);
        }
    }

    public  class  MessageAdapter extends BaseAdapter{

        List<JSONObject>  messagelist=new ArrayList<>();

        @Override
        public int getCount() {
            return messagelist.size();
        }

        @Override
        public Object getItem(int i) {
            return messagelist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.top, viewGroup, false);
            TextView sendmessage=view.findViewById(R.id.sentMessage);
            TextView ricevemessage=view.findViewById(R.id.receivedMessage);
            JSONObject item = messagelist.get(i);
            try {
                if (item.getBoolean("byServer")){
                    ricevemessage.setVisibility(View.VISIBLE);
                    ricevemessage.setText(item.getString("message"));
                    sendmessage.setVisibility(View.INVISIBLE);

                }
                else {
                    sendmessage.setVisibility(View.VISIBLE);
                    sendmessage.setText(item.getString("message"));
                    ricevemessage.setVisibility(View.INVISIBLE);

                }


            }catch (JSONException e){
                e.printStackTrace();
            }

            return view;
        }
        void addItem(JSONObject item){
            messagelist.add(item);
            notifyDataSetChanged();
        }

    }

}