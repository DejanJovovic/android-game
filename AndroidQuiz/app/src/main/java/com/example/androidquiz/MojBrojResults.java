package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidquiz.network.WebSocket;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import io.socket.client.Socket;


public class MojBrojResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj_results);
        Bundle extras = getIntent().getExtras();
        int hostScore = extras.getInt("hostScore");
        int guestScore = extras.getInt("guestScore");
        String solution = extras.getString("solution");

        TextView hostPoints = findViewById(R.id.hostPoints);
        TextView guestPoints = findViewById(R.id.guestPoints);
        TextView closerSolution = findViewById(R.id.closerSolution);
        Button finish = findViewById(R.id.finishGamesBtn);

        hostPoints.setText(String.valueOf(hostScore));
        guestPoints.setText(String.valueOf(guestScore));
        closerSolution.setText(solution + " = " + evalSolution(solution));

        finish.setOnClickListener(v -> {
            WebSocket.stop();
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        });

    }

    Double evalSolution(String solution) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            Object result = rhino.evaluateString(scope, solution, "JavaScript", 1, null);
            return Double.parseDouble(Context.toString(result));
        } finally {
            Context.exit();
        }
    }
}