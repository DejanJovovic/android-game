package com.example.androidquiz.network;

import android.util.Log;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import io.socket.client.IO;
import io.socket.client.Socket;

public class WebSocket {
    private static Socket socket;
    public static Socket getSocket() {
        return socket;
    }

    public static Socket connect(String ip) {
        try {
            stop();
            socket = IO.socket("http://" + ip + ":3000");
            socket.connect();
            return socket;
        } catch (Exception e) {
            return null;
        }
    }

    public static void stop() {
        if (socket != null) {
            socket.disconnect();
            socket.close();
            socket = null;
        }
    }
}
