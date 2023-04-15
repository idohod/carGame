package models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;

import com.bumptech.glide.Glide;
import logics.GameManager;

import com.example.carsgame.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class MainActivity extends AppCompatActivity {

    private final StoneActivity stoneActivity = new StoneActivity();
    private final carActivity carActivity = new carActivity();
    private final Handler handler = new Handler();
    private final int DELAY = 1200;
    private GameManager gameManager;
    private FloatingActionButton left_button;
    private FloatingActionButton right_button;
    private ShapeableImageView[] hearts;

   private AppCompatImageView backgroundImage;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            handler.postDelayed(this, DELAY);
           stoneActivity.changeAllStones();

            refreshUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        startView();
        gameManager = new GameManager(carActivity.getCars().length);
        Glide
                .with(this)
                .load("https://img.freepik.com/premium-photo/night-sky-with-many-stars_104337-8996.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(backgroundImage);


        left_button.setOnClickListener(v -> changeCarPosition());
        right_button.setOnClickListener(v -> changeCarPosition());
        handler.postDelayed(runnable, DELAY);
        refreshUI();

    }
    private void startView() {
        carActivity.getCars()[0].setVisibility(View.INVISIBLE);
        carActivity.getCars()[2].setVisibility(View.INVISIBLE);
        initStones();
    }

    private void initStones() {

        for (int i = 0; i < stoneActivity.getAllStones().length; i++) {
            for (int j = 0; j < stoneActivity.getAllStones()[0].length; j++) {
                if ((i== 0 && j == 0) || (i == 1 && j == 1) || (i == 2 && j == 0) ||
                       (i == 3 && j == 2) || (i == 4 && j == 1) || (i == 6 && j == 0)) {
                    stoneActivity.getAllStones()[i][j].setVisibility(View.VISIBLE);
                }else {
                    stoneActivity.getAllStones()[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void checkCrash() {

        for(int i = 0; i< carActivity.getCars().length; i++){
            if(carActivity.getCars()[i].getVisibility() == View.VISIBLE &&
            stoneActivity.getAllStones()[6][i].getVisibility()==View.VISIBLE){
                gameManager.setCrash(gameManager.getCrash() + 1);
                makeVibratorAndToast();
            }
        }

    }
    private void makeVibratorAndToast(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameManager.createVibrator(v);
        gameManager.createToast(getApplicationContext());

    }
    private void refreshUI()  {

        if(gameManager.isGameEnded()) {
            gameManager.setCrash(0);
        }

        if(gameManager.getCrash() == 0){
            for (ShapeableImageView heart : hearts) {
                heart.setVisibility(View.VISIBLE);
            }
        }
        else
            hearts[hearts.length - gameManager.getCrash()].setVisibility(View.INVISIBLE);

        carActivity.getCars()[carActivity.getCarCurPosition()].setVisibility(View.VISIBLE);
        carActivity.getCars()[carActivity.getCarPrePosition()].setVisibility(View.INVISIBLE);
        checkCrash();

        }

    private void findViews() {
        hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        carActivity.setCars(new ShapeableImageView[]{
                findViewById(R.id.left_car),
                findViewById(R.id.mid_car),
                findViewById(R.id.right_car)});

       stoneActivity.setAllStones(new ShapeableImageView[][]{
               {findViewById(R.id.stone00), findViewById(R.id.stone01),findViewById(R.id.stone02)},
               {findViewById(R.id.stone10), findViewById(R.id.stone11),findViewById(R.id.stone12)},
               {findViewById(R.id.stone20), findViewById(R.id.stone21),findViewById(R.id.stone22)},
               {findViewById(R.id.stone30), findViewById(R.id.stone31),findViewById(R.id.stone32)},
               {findViewById(R.id.stone40), findViewById(R.id.stone41),findViewById(R.id.stone42)},
               {findViewById(R.id.stone50), findViewById(R.id.stone51),findViewById(R.id.stone52)},
               {findViewById(R.id.stone60), findViewById(R.id.stone61),findViewById(R.id.stone62)}

       });

        left_button = findViewById(R.id.left_fab);
        right_button = findViewById(R.id.right_fab);
        backgroundImage = findViewById(R.id.background);

    }

    private void changeCarPosition() {
        if(right_button.isPressed() && carActivity.getCarCurPosition() < carActivity.getCars().length-1) {
            carActivity.setCarPrePosition(carActivity.getCarCurPosition());
            carActivity.setCarCurPosition(carActivity.getCarCurPosition()+1);

        }
        else if(left_button.isPressed() && carActivity.getCarCurPosition() > 0) {
            carActivity.setCarPrePosition(carActivity.getCarCurPosition());
            carActivity.setCarCurPosition(carActivity.getCarCurPosition()-1);

        }
        refreshUI();
    }
}



