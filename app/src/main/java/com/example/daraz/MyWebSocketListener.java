package com.example.daraz;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import android.os.Handler;
import android.os.Looper;

public class MyWebSocketListener extends WebSocketListener {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private OnWebSocketMessageReceived messageCallback;

    public MyWebSocketListener(OnWebSocketMessageReceived callback) {
        this.messageCallback = callback;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        uiHandler.post(() -> messageCallback.onOpen("WebSocket connected!"));
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        uiHandler.post(() -> messageCallback.onMessageReceived(text));
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        uiHandler.post(() -> messageCallback.onClosing("WebSocket closing: " + reason));
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        uiHandler.post(() -> messageCallback.onError(t.getMessage()));
    }

    public interface OnWebSocketMessageReceived {
        void onOpen(String message);
        void onMessageReceived(String message);
        void onClosing(String message);
        void onError(String error);
    }
}
