package com.example.androidquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.androidquiz.network.NetworkUtils;
import com.example.androidquiz.network.WebSocket;

import java.util.logging.Logger;

import io.socket.client.Socket;

public class HomePage extends AppCompatActivity {
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("Home");
    }

    private void initSocket() {
        socket.on("connected", args -> {
            runOnUiThread(() -> {
                LinearLayout connectionLayout = findViewById(R.id.connectionLayout);
                LinearLayout roomLayout = findViewById(R.id.roomLayout);
                connectionLayout.setVisibility(View.GONE);
                roomLayout.setVisibility(View.VISIBLE);
            });
        });

        socket.on("roomCreated", args -> {
            String roomId = (String) args[0];
            runOnUiThread(() -> {
                AlertDialog.Builder roomDialog = new AlertDialog.Builder(this);
                TextView roomTextView = new TextView(this);
                TextView waitingTextView = new TextView(this);
                roomTextView.setText("Your room id: " + roomId);
                waitingTextView.setText("Waiting for opponent");
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(roomTextView);
                layout.addView(waitingTextView);
                roomDialog.setView(layout);
                roomDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        socket.emit("destroyRoom", roomId);
                    }
                });
                roomDialog.show();
            });
        });

        socket.on("roomJoined", args -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Room joined", Toast.LENGTH_LONG).show();
            });
        });

        socket.on("roomNotFound", args -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Room not found!", Toast.LENGTH_LONG).show();
            });
        });

        socket.on("startGame", args -> {
            Intent intent = new Intent(this, KoZnaZnaActivity.class);
            startActivity(intent);
        });
    }

    public void handleCreate(View v) {
        v.setOnClickListener(btn -> {
            socket.emit("createRoom");
        });
    }

    public void handleJoin(View v) {
        AlertDialog.Builder roomDialog = new AlertDialog.Builder(this);
        EditText roomId = new EditText(this);
        roomDialog.setTitle("Enter room id");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(roomId);
        roomDialog.setView(layout);
        roomDialog.setPositiveButton("Join", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = roomId.getText().toString();
                socket.emit("joinRoom", text);

            }
        });
        roomDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        roomDialog.show();
    }

    public void profile(View v) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void games(View v) {
        Intent intent = new Intent(this, Games.class);
        startActivity(intent);
    }

    public void signOff(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void connectToServer(View v) {
        EditText ipField = findViewById(R.id.ipField);
        String ip = ipField.getText().toString();
        socket = WebSocket.connect(ip);
        initSocket();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}