package models;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carsgame.R;
import com.google.android.material.textview.MaterialTextView;

public class MyRecordActivity extends AppCompatActivity {
    private MaterialTextView myScoreText;
    private MaterialTextView myScore;
    private Button showAllRecords;
    private Button backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_record_activity);
         findViews();
        showAllRecords = findViewById(R.id.all_records_button);

        showAllRecords.setOnClickListener(v->showRecords());
         backMenu.setOnClickListener(v->backToMenu());
        setMyScore();

    }

    private void setMyScore(){
        Intent in = getIntent();
        int score = in.getIntExtra("MY_RECORD",0);
        myScore.setText(""+score);

    }
    private void backToMenu() {
        startActivity(new Intent(MyRecordActivity.this,MenuActivity.class));

    }

    private void showRecords() {
        startActivity(new Intent(MyRecordActivity.this,recordsActivity.class));
    }

    private void findViews() {
        backMenu = findViewById(R.id.back_button);
        myScore = findViewById(R.id.my_score);
        myScoreText = findViewById(R.id.my_score_text);

    }
}
