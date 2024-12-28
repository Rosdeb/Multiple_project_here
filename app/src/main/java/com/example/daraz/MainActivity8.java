package com.example.daraz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity8 extends AppCompatActivity {


    Button button;
    private WebSocket webSocket;
    private MessageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

                ListView messageList = findViewById(R.id.messagelist);
                final EditText messageBox = findViewById(R.id.messagebox);
                TextView send = findViewById(R.id.send);



                instantiateWebSocket();



                adapter = new MessageAdapter();
                messageList.setAdapter(adapter);


                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String message = messageBox.getText().toString();



                        if (!message.isEmpty()) {


                            webSocket.send(message);
                            messageBox.setText("");



                            JSONObject jsonObject = new JSONObject();

                            try {


                                jsonObject.put("message", message);
                                jsonObject.put("byServer", false);

                                adapter.addItem(jsonObject);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });



            }




            private void instantiateWebSocket() {



                OkHttpClient client = new OkHttpClient();


                //replace x.x.x.x with your machine's IP Address
                Request request = new Request.Builder().url("ws://192.168.31.96:8080").build();


                SocketListener socketListener = new SocketListener(this);


                webSocket = client.newWebSocket(request, socketListener);



            }





            public class SocketListener extends WebSocketListener {


                public MainActivity8 activity;



                public SocketListener(MainActivity8 activity) {
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

            public class MessageAdapter extends BaseAdapter {



                List<JSONObject> messagesList = new ArrayList<>();



                @Override
                public int getCount() {
                    return messagesList.size();
                }

                @Override
                public Object getItem(int i) {
                    return messagesList.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {

                    if (view == null)
                        view = getLayoutInflater().inflate(R.layout.top, viewGroup, false);


                    TextView sentMessage = view.findViewById(R.id.sentMessage);
                    TextView receivedMessage = view.findViewById(R.id.receivedMessage);
                    TextView sent = view.findViewById(R.id.send);


                    JSONObject item = messagesList.get(i);


                    try {

                        if (item.getBoolean("byServer")) {
                            receivedMessage.setVisibility(View.VISIBLE);
                            receivedMessage.setText(item.getString("message"));
                            sentMessage.setVisibility(View.INVISIBLE);


                        } else {

                            sentMessage.setVisibility(View.VISIBLE);
                            sentMessage.setText(item.getString("message"));
                            receivedMessage.setVisibility(View.INVISIBLE);
                            sent.setVisibility(View.VISIBLE);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    return view;
                }



                void addItem(JSONObject item) {


                    messagesList.add(item);
                    notifyDataSetChanged();


                }



            }

        }


