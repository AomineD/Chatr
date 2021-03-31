package com.app.chat.chat;

import android.util.Log;

import androidx.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketListner extends WebSocketListener {

    private Mediator mediator;

    private static final int CLOSURE_STATUS = 1000;
    private WebSocket webSocket = null;

    public SocketListner(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
        mediator.onConnected(webSocket, response);
    }

    public void sendMsg(String msg) {
        if (webSocket != null)
            webSocket.send(msg);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        mediator.onFailure(webSocket, t.getLocalizedMessage());
        Log.e("Failure :", t.getMessage());
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        mediator.closingOrClosed(true, webSocket, reason, code);
        Log.e("Closed :", reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        mediator.closingOrClosed(false, webSocket, reason, code);
        Log.e("Closing :", reason);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        mediator.getMessage(text);
    }

    public interface Mediator {
        void onConnected(WebSocket webSocket, Response response);

        void getMessage(String msg);

        void onFailure(WebSocket webSocket, String reason);

        void closingOrClosed(boolean isClosed, WebSocket webSocket, String reason, int code);

    }

    public WebSocket getWebSocket() {
        return webSocket;
    }
}
