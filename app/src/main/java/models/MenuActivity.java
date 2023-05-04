package models;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsgame.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import logics.CarManager;

public class MenuActivity extends AppCompatActivity {

    private MaterialTextView menuTitle;
    private MaterialButton slowButton;
    private MaterialButton fastButton;
    private MaterialButton sensorButton;

    private MaterialButton RecordsButton;

private CarManager carManager=new CarManager();
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        slowButton.setOnClickListener(v -> setDelay());
        fastButton.setOnClickListener(v -> setDelay());
        sensorButton.setOnClickListener(v -> SetSensors());
        RecordsButton.setOnClickListener(v -> showRecords());
        menuTitle.setText("Cars Game");
    }

    private void showRecords() {
        Intent i= new Intent(MenuActivity.this, RecordsActivity.class);
        i.putExtra("",2);
        startActivity(i);

    }

    private void SetSensors() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        intent.putExtra("KEY_SENSOR", 1);
        startActivity(intent);
        Log.d(TAG, "cur: "+carManager.getCarCurPosition()+ "pre: "+carManager.getCarPrePosition());
    }

    private void setDelay() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        if (slowButton.isPressed()) {
            intent.putExtra("KEY_DELAY", 1200);

        } else if (fastButton.isPressed()) {
            intent.putExtra("KEY_DELAY", 500);
        }
        startActivity(intent);
    }

    private void findViews() {
        menuTitle = findViewById(R.id.menu_title);
        slowButton = findViewById(R.id.slow_button);
        fastButton = findViewById(R.id.fast_button);
        sensorButton = findViewById(R.id.sensor_button);
        RecordsButton = findViewById(R.id.records_button);
    }
}


