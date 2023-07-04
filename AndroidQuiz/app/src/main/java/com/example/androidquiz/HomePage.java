package com.example.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

import io.socket.client.Socket;


public class HomePage extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    String userId;
    Socket socket;
    boolean isHost = true;
    String roomId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("Home");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        final TextView userTextView = findViewById(R.id.userTxt);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String username = userProfile.username;

                    userTextView.setText("Welcome " + username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });



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
            roomId = (String) args[0];
            isHost = true;
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
            roomId = (String) args[0];
            isHost = false;
            runOnUiThread(() -> {
                Toast.makeText(this, "Room joined", Toast.LENGTH_LONG).show();
            });
        });

        socket.on("roomNotFound", args -> {
            roomId = "";
            runOnUiThread(() -> {
                Toast.makeText(this, "Room not found!", Toast.LENGTH_LONG).show();
            });
        });

        socket.on("startGame", args -> {
            Intent intent = new Intent(this, MojBroj.class);
            Bundle extras = new Bundle();
            extras.putBoolean("isHost", isHost);
            extras.putString("roomId", roomId);
            intent.putExtras(extras);
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