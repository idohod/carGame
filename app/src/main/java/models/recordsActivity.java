package models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.carsgame.R;
import fragments.ListFragment;
import fragments.MapsFragment;
import interfaces.RecordCallback;

public class recordsActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private MapsFragment mapsFragment;
    RecordCallback recordCallback = new RecordCallback() {
        @Override
        public void itemClicked(double latitude, double longitude) {
            mapsFragment.goToLocation(latitude, longitude);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        initFragments();
        beginTransactions();

    }


    private void initFragments() {
        listFragment = new ListFragment();
        mapsFragment = new MapsFragment();
        listFragment.setCallBack(recordCallback);
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
    }
}