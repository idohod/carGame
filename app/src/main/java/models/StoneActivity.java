package models;


import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

public class StoneActivity {

    private ShapeableImageView[][] allStones;

    public StoneActivity() {}
    public ShapeableImageView[][] getAllStones() {
        return allStones;
    }

    public void setAllStones(ShapeableImageView[][] allStones) {
        this.allStones = allStones;
    }

    public void changeAllStones() {
        changeStone1Position();
        changeStone2Position();
        changeStone3Position();
        changeStone4Position();
        changeStone5Position();
        changeStone6Position();
    }
    private int stone1PrePosition = 0;
    private int stone1CurPosition = 1;
    private int stone2PrePosition = 1;
    private int stone2CurPosition = 2;
    private int stone3PrePosition = 2;
    private int stone3CurPosition = 3;
    private int stone4PrePosition = 3;
    private int stone4CurPosition = 4;
    private int stone5PrePosition = 4;
    private int stone5CurPosition = 5;
    private int stone6PrePosition = 6;
    private int stone6CurPosition = 0;


    private void changeStone1Position() {
           if (stone1PrePosition == allStones.length) {

               stone1PrePosition = 0;
           }
           if (stone1CurPosition == allStones.length) {
               stone1CurPosition = 0;
           }

           if (allStones[stone1PrePosition][0].getVisibility() == View.VISIBLE) {
               allStones[stone1PrePosition][0].setVisibility(View.INVISIBLE);
               allStones[stone1CurPosition][0].setVisibility(View.VISIBLE);

               stone1PrePosition++;
               stone1CurPosition++;
           }
       }

    private void changeStone2Position() {
        if (stone2PrePosition == allStones.length) {

            stone2PrePosition = 0;
        }
        if (stone2CurPosition == allStones.length) {
            stone2CurPosition = 0;
        }

        if (allStones[stone2PrePosition][1].getVisibility() == View.VISIBLE) {
            allStones[stone2PrePosition][1].setVisibility(View.INVISIBLE);
            allStones[stone2CurPosition][1].setVisibility(View.VISIBLE);

            stone2PrePosition++;
            stone2CurPosition++;
        }
    }

    private void changeStone3Position() {
        if (stone3PrePosition == allStones.length) {

            stone3PrePosition = 0;
        }
        if (stone3CurPosition == allStones.length) {
            stone3CurPosition = 0;
        }

        if (allStones[stone3PrePosition][0].getVisibility() == View.VISIBLE) {
            allStones[stone3PrePosition][0].setVisibility(View.INVISIBLE);
            allStones[stone3CurPosition][0].setVisibility(View.VISIBLE);

            stone3PrePosition++;
            stone3CurPosition++;
        }
    }

    private void changeStone4Position() {
        if (stone4PrePosition == allStones.length) {

            stone4PrePosition = 0;
        }
        if (stone4CurPosition == allStones.length) {
            stone4CurPosition = 0;
        }

        if (allStones[stone4PrePosition][2].getVisibility() == View.VISIBLE) {
            allStones[stone4PrePosition][2].setVisibility(View.INVISIBLE);
            allStones[stone4CurPosition][2].setVisibility(View.VISIBLE);

            stone4PrePosition++;
            stone4CurPosition++;
        }
    }

    private void changeStone5Position() {
        if (stone5PrePosition == allStones.length) {
            stone5PrePosition = 0;
        }
        if (stone5CurPosition == allStones.length) {
            stone5CurPosition = 0;
        }
        if (allStones[stone5PrePosition][1].getVisibility() == View.VISIBLE) {
            allStones[stone5PrePosition][1].setVisibility(View.INVISIBLE);
            allStones[stone5CurPosition][1].setVisibility(View.VISIBLE);
            stone5PrePosition++;
            stone5CurPosition++;
        }
    }
    private void changeStone6Position() {
        if (stone6PrePosition == allStones.length) {

            stone6PrePosition = 0;
        }
        if (stone6CurPosition == allStones.length) {
            stone6CurPosition = 0;
        }

        if (allStones[stone6PrePosition][0].getVisibility() == View.VISIBLE) {
            allStones[stone6PrePosition][0].setVisibility(View.INVISIBLE);
            allStones[stone6CurPosition][0].setVisibility(View.VISIBLE);

            stone6PrePosition++;
            stone6CurPosition++;
        }

    }

}



