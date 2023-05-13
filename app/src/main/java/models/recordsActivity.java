package models;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carsgame.R;

import fragments.ListFragment;
import fragments.MapsFragment;

public class recordsActivity extends AppCompatActivity {
    private ListFragment listFragment;

    private MapsFragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initFragments();
        beginTransactions();


    }



    private void initFragments() {
        listFragment = new ListFragment();
       // listFragment.setCallBack(recordCallback);
         mapsFragment = new MapsFragment();

    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapsFragment).commit();

        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
    }
}