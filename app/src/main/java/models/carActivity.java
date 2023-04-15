package models;

import com.google.android.material.imageview.ShapeableImageView;

public class carActivity {

    private int carCurPosition = 1;
    private int carPrePosition = 0;

    private ShapeableImageView[] cars;

    public ShapeableImageView[] getCars() {
        return cars;
    }

    public void setCars(ShapeableImageView[] cars) {
        this.cars = cars;
    }

    public carActivity() {}


    public int getCarCurPosition() {
        return carCurPosition;
    }

    public void setCarCurPosition(int carCurPosition) {
        this.carCurPosition = carCurPosition;
    }

    public int getCarPrePosition() {
        return carPrePosition;
    }

    public void setCarPrePosition(int carPrePosition) {
        this.carPrePosition = carPrePosition;
    }
}
