package models;

import static com.example.carsgame.R.id.main_FRAME_map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.carsgame.R;
import com.google.android.gms.maps.MapFragment;

import fragments.ListFragment;
import fragments.MapFragment;

public class recordsActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initFragments();
        beginTransactions();

    }

    private void initFragments() {
        listFragment = new ListFragment();
       // listFragment.setCallBack();
        mapFragment = new MapFragment();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }
}