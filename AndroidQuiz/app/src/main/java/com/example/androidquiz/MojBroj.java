package com.example.androidquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidquiz.network.WebSocket;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import io.socket.client.Socket;
import kotlin.random.Random;

public class MojBroj extends AppCompatActivity implements SensorListener {
    boolean isHost;
    String roomId;
    private Socket socket;
    private SensorManager sensorManager;
    private static final int SHAKE_THRESHOLD = 800;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastTime;
    boolean generated = false;
    TextView timerText;
    CountDownTimer timeLeft;
    CountDownTimer generateTimer;
    List<Button> input = new LinkedList<>();
    int currentNumber = 0;

    void initTimer() {
        timerText = findViewById(R.id.timeLeft);
        timeLeft = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
        timeLeft.start();
    }

    void initSocket() {
        if (socket == null) {
            return;
        }
        socket.on("mojBroj_numberGenerated", args -> {
            String number = (String) args[0];
            int idx = (Integer) args[1];
            runOnUiThread(() -> {
                setNumber(number, idx);
            });
        });
    }

    void init() {
        socket = WebSocket.getSocket();
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);
        Button minus = findViewById(R.id.minusBtn);
        Button plus = findViewById(R.id.plusBtn);
        Button multiply = findViewById(R.id.multiplyBtn);
        Button divide = findViewById(R.id.divideBtn);
        Button openBracket = findViewById(R.id.openBracketBtn);
        Button closedBracket = findViewById(R.id.closedBracketBtn);
        Button delete = findViewById(R.id.delBtn);

        singleDigit0.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit1.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit2.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        singleDigit3.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        mediumDigit.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        bigDigit.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
            v.setEnabled(false);
        });
        plus.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        minus.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        multiply.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        divide.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        openBracket.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        closedBracket.setOnClickListener(v -> {
            input.add((Button)v);
            displayInput();
        });
        delete.setOnClickListener(v -> {
            if (input.size() == 0) {

            } else {
            input.get(input.size()-1).setEnabled(true);
            input.remove(input.size()-1);
            displayInput();
            }
        });
    }

    void displayInput() {
        StringBuffer buffer = new StringBuffer();
        for (Button value : input) {
            buffer.append(value.getText().toString());
        }
        TextView inputText = findViewById(R.id.userInput);
        inputText.setText(buffer.toString());
    }

    void genNumbers() {
        if (generated) return;

        String[] singleDigits = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] mediumDigits = {"10", "15", "20"};
        String[] bigDigits = {"25", "50", "75", "100"};

        int idx = 0;
        String generatedNumber = null;
        if (currentNumber == 0) {
            generatedNumber = "" + Random.Default.nextInt(2, 1000);
        } else if (currentNumber> 0 && currentNumber < 5) {
            idx = Random.Default.nextInt(singleDigits.length);
            generatedNumber = singleDigits[idx];
        } else if (currentNumber == 5) {
            idx = Random.Default.nextInt(mediumDigits.length);
            generatedNumber = mediumDigits[idx];
        } else if (currentNumber == 6) {
            idx = Random.Default.nextInt(bigDigits.length);
            generatedNumber = bigDigits[idx];
        }
        setNumber(generatedNumber, currentNumber);
        if (socket != null) {
            socket.emit("mojBroj_generateNumber", roomId, generatedNumber, currentNumber);
        }
        currentNumber ++;
    }

    void setNumber(String number, int idx) {
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);
        Button result = findViewById(R.id.finalResult);

        if (idx == 0) {
            result.setText(number);
        } else if (idx == 1) {
            singleDigit0.setText(number);
        } else if (idx == 2) {
            singleDigit1.setText(number);
        } else if (idx == 3) {
            singleDigit2.setText(number);
        } else if (idx == 4) {
            singleDigit3.setText(number);
        } else if (idx == 5) {
            mediumDigit.setText(number);
        } else if (idx == 6) {
            enableButtons();
           generated = true;
            bigDigit.setText(number);
        }


    }

    void enableButtons() {
        Button singleDigit0 = findViewById(R.id.singleDigit0);
        Button singleDigit1 = findViewById(R.id.singleDigit1);
        Button singleDigit2 = findViewById(R.id.singleDigit2);
        Button singleDigit3 = findViewById(R.id.singleDigit3);
        Button mediumDigit = findViewById(R.id.mediumDigit);
        Button bigDigit = findViewById(R.id.bigDigit);
        Button minus = findViewById(R.id.minusBtn);
        Button plus = findViewById(R.id.plusBtn);
        Button multiply = findViewById(R.id.multiplyBtn);
        Button divide = findViewById(R.id.divideBtn);
        Button openBracket = findViewById(R.id.openBracketBtn);
        Button closedBracket = findViewById(R.id.closedBracketBtn);
        Button delete = findViewById(R.id.delBtn);
        singleDigit0.setEnabled(true);
        singleDigit1.setEnabled(true);
        singleDigit2.setEnabled(true);
        singleDigit3.setEnabled(true);
        mediumDigit.setEnabled(true);
        bigDigit.setEnabled(true);
        minus.setEnabled(true);
        plus.setEnabled(true);
        multiply.setEnabled(true);
        divide.setEnabled(true);
        openBracket.setEnabled(true);
        closedBracket.setEnabled(true);
        delete.setEnabled(true);
    }

    Double evalSolution() {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        TextView solution = findViewById(R.id.userInput);
        try {
            Scriptable scope = rhino.initStandardObjects();
            Object result = rhino.evaluateString(scope, solution.getText().toString(), "JavaScript", 1, null);
            return Double.parseDouble(Context.toString(result));
        } finally {
            Context.exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeLeft != null) {
            timeLeft.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        isHost = getIntent().getExtras().getBoolean("isHost");
        roomId = getIntent().getExtras().getString("roomId");
        Toast.makeText(this, "Host:" + isHost, Toast.LENGTH_LONG).show();
        setTitle("Moj broj");
        init();
        initTimer();
        initSocket();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        generateTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (!generated) {
                    genNumbers();
                    generateTimer.start();
                }
            }
        };
        if (isHost) {
            generateTimer.start();
        }
        Button result = findViewById(R.id.finalResult);
        Button finish = findViewById(R.id.finishBtn);
        finish.setOnClickListener(v -> {
            timeLeft.cancel();
            Double actual = Double.parseDouble(result.getText().toString());
            Double guessed = evalSolution();
            TextView solution = findViewById(R.id.userInput);
            solution.setText(guessed.toString());
            if (actual == guessed) {
                Toast.makeText(getApplicationContext(), "Osvojili ste 20 poena!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Osvojili ste 5 poena!", Toast.LENGTH_LONG).show();
            }
        });

        Button stopBtn = findViewById(R.id.stopBtn);
        if (!isHost) {
            stopBtn.setEnabled(false);
        }
        stopBtn.setOnClickListener(v -> {
            genNumbers();
            generateTimer.cancel();
            generateTimer.start();
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MojBroj.this, Games.class));
        finish();
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor != SensorManager.SENSOR_ACCELEROMETER) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > 500) {
            long delta = currentTime - lastTime;
            lastTime = currentTime;

            float x = values[SensorManager.DATA_X];
            float y = values[SensorManager.DATA_Y];
            float z = values[SensorManager.DATA_Z];
            float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / delta * 10000;
            if (speed > SHAKE_THRESHOLD && isHost) {
                genNumbers();
                generateTimer.cancel();
                generateTimer.start();
            }
            lastX = x;
            lastY = y;
            lastZ = z;

        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

}