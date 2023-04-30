package logics;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class GameManager {
    private int crash;
    private int life;

    private int score;

    public GameManager(int life) {
        this.crash = 0;
        this.score = 0;
        this.life = life;
    }
    public boolean isGameEnded(){
        return crash == life;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getCrash() {
        return crash;
    }

    public void setCrash(int crash) {
        this.crash = crash;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }





}
