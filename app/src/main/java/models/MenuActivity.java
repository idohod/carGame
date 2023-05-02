package models;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsgame.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {

    private MaterialTextView menuTitle;
    private MaterialButton slowButton;
    private MaterialButton fastButton;


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        slowButton.setOnClickListener(v -> onClick());
        fastButton.setOnClickListener(v -> onClick());
        menuTitle.setText("Cars Game");
    }

    private void onClick() {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        if(slowButton.isPressed()){
            intent.putExtra("KEY_DELAY", 1200);

        } else if (fastButton.isPressed()) {
            intent.putExtra("KEY_DELAY", 600);
        }
        startActivity(intent);
    }
    private void findViews() {
        menuTitle  = findViewById(R.id.menu_title);
        slowButton = findViewById(R.id.slow_button);
        fastButton = findViewById(R.id.fast_button);
    }

}
