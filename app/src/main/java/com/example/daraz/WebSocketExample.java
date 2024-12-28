package com.example.daraz;

import okhttp3.*;

public class WebSocketExample{
    private OkHttpClient client;
    private WebSocket webSocket;
    private WebSocketListener listener;

    public WebSocketExample(String url, WebSocketListener listener) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        this.listener = listener;
        webSocket = client.newWebSocket(request, listener);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    public void closeConnection() {
        if (webSocket != null) {
            webSocket.close(1000, "Goodbye!");
        }
    }
}
