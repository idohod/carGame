package models;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import interfaces.SensorCallback;
import logics.App;
import logics.CarManager;
import logics.CoinManager;
import logics.GameManager;
import logics.StoneManager;
import utilities.MySP;
import android.app.Application;
import android.widget.Toast;

import com.example.carsgame.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final StoneManager stoneManager = new StoneManager();
    private final CarManager carManager = new CarManager();
    private final CoinManager coinManager = new CoinManager();

    private final App app = new App();

    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private int DELAY = 0;
    private GameManager gameManager;
    private FloatingActionButton left_button;
    private FloatingActionButton right_button;
    private TextView textScore;

    private ShapeableImageView[] hearts;

    private AppCompatImageView backgroundImage;

    private RecordList allRecords;
    private SensorMove sensorMove;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            coinManager.changeAllCoins();
            stoneManager.changeAllStones();
            refreshUI();
        }
    };


    private void setDelay() {
        Intent i = getIntent();
        DELAY = i.getIntExtra("KEY_DELAY", 0);
    }

    private boolean sensorMode() {
        Intent i = getIntent();
        int flag = i.getIntExtra("KEY_SENSOR", 0);
        return flag == 1;
    }
    private void load(){

        String fromSP =  MySP.getInstance().getString("RECORDS","");
        RecordList recordListFromJson = new Gson().fromJson(fromSP,RecordList.class );
        if(allRecords == null){
            allRecords = new RecordList();
        }
        allRecords.setAllRecords(recordListFromJson.getAllRecords());

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        startView();
        setDelay();
        gameManager = new GameManager(hearts.length);
         load();


        Glide
                .with(this)
                .load("https://img.freepik.com/premium-photo/night-sky-with-many-stars_104337-8996.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(backgroundImage);

        if (!sensorMode()) {
            left_button.setOnClickListener(v -> changeCarPosition());
            right_button.setOnClickListener(v -> changeCarPosition());

        } else {
            DELAY = 1000;
            InitSensorMove();
        }
        handler.postDelayed(runnable, DELAY);
        refreshUI();

    }

    private void InitSensorMove() {
        sensorMove = new SensorMove(this, new SensorCallback() {
            @Override
            public void stepX() {
                carManager.setCarCurPosition(sensorMove.getCarPositionX());
                carManager.setCarPrePosition(sensorMove.getCarPrePosition());

                refreshUI();
            }

            @Override
            public void stepY() {

            }

            @Override
            public void stepZ() {

            }
        });
    }


    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        if (sensorMode()) {
            sensorMove.stop();
        }
    }

    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!handler.hasCallbacks(runnable)) {
                handler.postDelayed(runnable, DELAY);
            }
        }
        if (sensorMode()) {
            sensorMove.start();
        }
    }

    private void startView() {
        if (sensorMode()) {
            left_button.setVisibility(View.INVISIBLE);
            right_button.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < carManager.getCars().length; i++) {
            if (i != 2) {
                carManager.getCars()[i].setVisibility(View.INVISIBLE);
            }
        }
        initStones();
        initCoins();
    }

    private void initCoins() {

        for (int i = 0; i < coinManager.getAllCoins().length; i++) {
            for (int j = 0; j < coinManager.getAllCoins()[0].length; j++) {
                if ((i == 0 && j == 4) || (i == 2 && j == 1)) {
                    coinManager.getAllCoins()[i][j].setVisibility(View.VISIBLE);
                } else {
                    coinManager.getAllCoins()[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void initStones() {

        for (int i = 0; i < stoneManager.getAllStones().length; i++) {
            for (int j = 0; j < stoneManager.getAllStones()[0].length; j++) {
                if ((i == 7 && j == 3) || (i == 0 && j == 1) || (i == 2 && j == 0) ||
                        (i == 3 && j == 2) || (i == 4 && j == 1) || (i == 6 && j == 0) ||
                        (i == 2 && j == 3) || (i == 5 && j == 4)) {
                    stoneManager.getAllStones()[i][j].setVisibility(View.VISIBLE);
                } else {
                    stoneManager.getAllStones()[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private boolean checkCrash() {
        for (int i = 0; i < carManager.getCars().length; i++) {
            if (carManager.getCars()[i].getVisibility() == View.VISIBLE &&
                    stoneManager.getAllStones()[stoneManager.getAllStones().length - 1][i].getVisibility() == View.VISIBLE) {
                gameManager.setCrash(gameManager.getCrash() + 1);
                mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
                makeVibratorAndToast();
                return true;
            }
        }
        return false;

    }

    private boolean collectCoin() {
        for (int i = 0; i < carManager.getCars().length; i++) {
            if (carManager.getCars()[i].getVisibility() == View.VISIBLE &&
                    coinManager.getAllCoins()[coinManager.getAllCoins().length - 1][i].getVisibility() == View.VISIBLE) {
                coinManager.getAllCoins()[coinManager.getAllCoins().length - 1][i].setVisibility(View.INVISIBLE);
                coinManager.getAllCoins()[0][i].setVisibility(View.VISIBLE);

                if (i == 4) {
                    coinManager.setCoin1PrePosition(0);
                    coinManager.setCoin1CurPosition(1);
                } else if (i == 1) {
                    coinManager.setCoin2PrePosition(0);
                    coinManager.setCoin2CurPosition(1);

                }
                helpCollectCoinView();
                mediaPlayer = MediaPlayer.create(this, R.raw.collectcoin);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
                return true;
            }
        }
        return false;
    }

    private void helpCollectCoinView() {
        for (int i = 0; i < coinManager.getAllCoins()[0].length; i++) {
            if (coinManager.getAllCoins()[0][i].getVisibility() == View.VISIBLE &&
                    stoneManager.getAllStones()[0][i].getVisibility() == View.VISIBLE) {
                coinManager.getAllCoins()[0][i].setVisibility(View.INVISIBLE);
                coinManager.getAllCoins()[1][i].setVisibility(View.VISIBLE);

                if (i == 4) {
                    coinManager.setCoin1PrePosition(1);
                    coinManager.setCoin1CurPosition(2);
                } else if (i == 1) {
                    coinManager.setCoin2PrePosition(1);
                    coinManager.setCoin2CurPosition(2);
                }
            }
        }
    }

    private void makeVibratorAndToast() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        app.createVibrator(v);
        app.createToast(getApplicationContext());

    }

    private void refreshUI() {
        heartsView();
        carView();
        changeScore();
    }

    private void carView() {
        carManager.getCars()[carManager.getCarCurPosition()].setVisibility(View.VISIBLE);
        carManager.getCars()[carManager.getCarPrePosition()].setVisibility(View.INVISIBLE);
    }

    private void heartsView() {
        if (gameManager.getCrash() == 0) {
            for (ShapeableImageView heart : hearts) {
                heart.setVisibility(View.VISIBLE);
            }
        } else
            hearts[hearts.length - gameManager.getCrash()].setVisibility(View.INVISIBLE);

        if (gameManager.isGameEnded()) {
            hearts[0].setVisibility(View.INVISIBLE);
            gameManager.setCrash(0);
            addRecord();
            Intent in = new Intent(MainActivity.this,MyRecordActivity.class);
            in.putExtra("MY_RECORD",gameManager.getScore());
            startActivity(in);

        }
    }

    private void addRecord() {

        Record newRecord = new Record(gameManager.getScore());
        putRecordInPosition(newRecord);
        String recordsListJson = new Gson().toJson(allRecords);
        MySP.getInstance().putString("RECORDS", recordsListJson);

    }
   private Record maxRecord(){
        Record max = allRecords.getAllRecords().get(0);
        for(int i=0;i<allRecords.getAllRecords().size();i++){
            if(allRecords.getAllRecords().get(i).getScore()>max.getScore()){
                max = allRecords.getAllRecords().get(i);
            }
        }
        return max;
   }
   private boolean highestRecordFirst(Record newRecord){

       if(allRecords.getAllRecords().size() >0) {
           if (newRecord.getScore() >= maxRecord().getScore()) {
               if (allRecords.getAllRecords().size() == 10) {
                   allRecords.getAllRecords().remove(9);
                   allRecords.getAllRecords().add(0,newRecord);
                   return true;

               } else if (allRecords.getAllRecords().size() < 10) {
                        allRecords.getAllRecords().add(0,newRecord);

                   return true;
               }
           }
       }
       return false;
   }

    private void putRecordInPosition(Record newRecord) {
        if (allRecords.getAllRecords().size() == 0) {
            allRecords.getAllRecords().add(newRecord);
        }

        else if (allRecords.getAllRecords().size() == 1) {
            if (newRecord.getScore() < allRecords.getAllRecords().get(0).getScore())
                allRecords.getAllRecords().add(newRecord);
            else
                highestRecordFirst(newRecord);
        }
         else{
            if (!highestRecordFirst(newRecord)) {
                for (int i = 1; i < allRecords.getAllRecords().size(); i++) {
                    if (newRecord.getScore() < allRecords.getAllRecords().get(i - 1).getScore() &&
                            newRecord.getScore() >= allRecords.getAllRecords().get(i).getScore()) {
                        allRecords.getAllRecords().add(i, newRecord);

                        if (allRecords.getAllRecords().size() == 11) {
                            allRecords.getAllRecords().remove(10);
                            return;
                        }
                    }

                }
                if(allRecords.getAllRecords().size()<10) {
                    allRecords.getAllRecords().add(newRecord);
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void formatScore() {
        if (gameManager.getScore() < 10)
            textScore.setText("00" + gameManager.getScore());
        else if (gameManager.getScore() >= 10 && gameManager.getScore() <= 99)
            textScore.setText("0" + gameManager.getScore());
        else
            textScore.setText("" + gameManager.getScore());
    }

    public void changeScore() {

        if (!sensorMode() && (left_button.isPressed() || right_button.isPressed()))
            gameManager.setScore(gameManager.getScore());
        else if (collectCoin()) {
            gameManager.setScore(gameManager.getScore() + 20);
        } else if (checkCrash()) {
            if (gameManager.getScore() >= 5)
                gameManager.setScore(gameManager.getScore() - 5);
            else if (gameManager.getScore() >= 0 && gameManager.getScore() < 5)
                gameManager.setScore(0);
        } else
            gameManager.setScore(gameManager.getScore() + 1);

        formatScore();
    }


    public void changeCarPosition() {
        if (!sensorMode()) {
            if (right_button.isPressed() && carManager.getCarCurPosition() < carManager.getCars().length - 1) {
                carManager.setCarPrePosition(carManager.getCarCurPosition());
                carManager.setCarCurPosition(carManager.getCarCurPosition() + 1);
            } else if (left_button.isPressed() && carManager.getCarCurPosition() > 0) {
                carManager.setCarPrePosition(carManager.getCarCurPosition());
                carManager.setCarCurPosition(carManager.getCarCurPosition() - 1);

            }
        }
        refreshUI();
        checkCrash();
        collectCoin();

    }

    private void findViews() {
        hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        carManager.setCars(new ShapeableImageView[]{
                findViewById(R.id.car0),
                findViewById(R.id.car1),
                findViewById(R.id.car2),
                findViewById(R.id.car3),
                findViewById(R.id.car4)});

        stoneManager.setAllStones(new ShapeableImageView[][]{
                {findViewById(R.id.stone00), findViewById(R.id.stone01), findViewById(R.id.stone02), findViewById(R.id.stone03), findViewById(R.id.stone04)},
                {findViewById(R.id.stone10), findViewById(R.id.stone11), findViewById(R.id.stone12), findViewById(R.id.stone13), findViewById(R.id.stone14)},
                {findViewById(R.id.stone20), findViewById(R.id.stone21), findViewById(R.id.stone22), findViewById(R.id.stone23), findViewById(R.id.stone24)},
                {findViewById(R.id.stone30), findViewById(R.id.stone31), findViewById(R.id.stone32), findViewById(R.id.stone33), findViewById(R.id.stone34)},
                {findViewById(R.id.stone40), findViewById(R.id.stone41), findViewById(R.id.stone42), findViewById(R.id.stone43), findViewById(R.id.stone44)},
                {findViewById(R.id.stone50), findViewById(R.id.stone51), findViewById(R.id.stone52), findViewById(R.id.stone53), findViewById(R.id.stone54)},
                {findViewById(R.id.stone60), findViewById(R.id.stone61), findViewById(R.id.stone62), findViewById(R.id.stone63), findViewById(R.id.stone64)},
                {findViewById(R.id.stone70), findViewById(R.id.stone71), findViewById(R.id.stone72), findViewById(R.id.stone73), findViewById(R.id.stone74)},
                {findViewById(R.id.stone80), findViewById(R.id.stone81), findViewById(R.id.stone82), findViewById(R.id.stone83), findViewById(R.id.stone84)}

        });
        coinManager.setAllCoins(new ShapeableImageView[][]{
                {findViewById(R.id.coin00), findViewById(R.id.coin01), findViewById(R.id.coin02), findViewById(R.id.coin03), findViewById(R.id.coin04)},
                {findViewById(R.id.coin10), findViewById(R.id.coin11), findViewById(R.id.coin12), findViewById(R.id.coin13), findViewById(R.id.coin14)},
                {findViewById(R.id.coin20), findViewById(R.id.coin21), findViewById(R.id.coin22), findViewById(R.id.coin23), findViewById(R.id.coin24)},
                {findViewById(R.id.coin30), findViewById(R.id.coin31), findViewById(R.id.coin32), findViewById(R.id.coin33), findViewById(R.id.coin34)},
                {findViewById(R.id.coin40), findViewById(R.id.coin41), findViewById(R.id.coin42), findViewById(R.id.coin43), findViewById(R.id.coin44)},
                {findViewById(R.id.coin50), findViewById(R.id.coin51), findViewById(R.id.coin52), findViewById(R.id.coin53), findViewById(R.id.coin54)},
                {findViewById(R.id.coin60), findViewById(R.id.coin61), findViewById(R.id.coin62), findViewById(R.id.coin63), findViewById(R.id.coin64)},
                {findViewById(R.id.coin70), findViewById(R.id.coin71), findViewById(R.id.coin72), findViewById(R.id.coin73), findViewById(R.id.coin74)},
                {findViewById(R.id.coin80), findViewById(R.id.coin81), findViewById(R.id.coin82), findViewById(R.id.coin83), findViewById(R.id.coin84)}

        });

        left_button = findViewById(R.id.left_fab);
        right_button = findViewById(R.id.right_fab);


        textScore = findViewById(R.id.score);
        backgroundImage = findViewById(R.id.background);
    }


}



