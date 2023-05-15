package models;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.carsgame.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {

    private MaterialTextView menuTitle;
    private MaterialTextView buttonsTitle;
    private MaterialTextView sensorTitle;
    private ExtendedFloatingActionButton slowButton;
    private ExtendedFloatingActionButton fastButton;
    private ExtendedFloatingActionButton sensorButton;

    private ExtendedFloatingActionButton RecordsButton;
    private AppCompatImageView backgroundImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        Glide
                .with(this)
                .load("https://wallpaperaccess.com/full/184117.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(backgroundImage);

        slowButton.setOnClickListener(v -> setDelay());
        fastButton.setOnClickListener(v -> setDelay());
        sensorButton.setOnClickListener(v -> SetSensors());
        RecordsButton.setOnClickListener(v -> showRecords());

    }
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);

    }
    public void onDestroy() {
        super.onDestroy();
    }
    protected void onPause() {
        super.onPause();

    }

    protected void onResume() {
        super.onResume();

    }
    private void showRecords() {
        Intent i = new Intent(MenuActivity.this, recordsActivity.class);
        startActivity(i);

    }


    private void SetSensors() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        intent.putExtra("KEY_SENSOR", 1);
        startActivity(intent);
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
        backgroundImage = findViewById(R.id.background);

        menuTitle = findViewById(R.id.menu_title);
        buttonsTitle = findViewById(R.id.speed_mode);
        sensorTitle = findViewById(R.id.sensor_mode);

        fastButton = findViewById(R.id.fast_button);
        slowButton = findViewById(R.id.slow_button);
        sensorButton = findViewById(R.id.sensor_button);
        RecordsButton = findViewById(R.id.records_button);
    }
}


