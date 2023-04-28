package logics;

import com.google.android.material.imageview.ShapeableImageView;

public class CarManager  {
    private int carCurPosition = 2;
    private int carPrePosition = 1;

    private ShapeableImageView[] cars;

    public ShapeableImageView[] getCars() {
        return cars;
    }

    public void setCars(ShapeableImageView[] cars) {
        this.cars = cars;
    }

    public CarManager() {}

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
