package utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import interfaces.SensorCallback;

public class SensorMove {
    private final Sensor sensor;

    private final SensorManager sensorManager;

    private final SensorCallback sensorCallback;

    private int carPosition = 2;
    private int carPrePosition = 1;
    private int delay = 0;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public SensorMove(Context context, SensorCallback sensorCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorCallback = sensorCallback;
        initEventListener();

    }


    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                carMovement(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

       private void carMovement(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x < -2.0 && carPosition < 4) {
                carPrePosition = carPosition;
                carPosition++;

                if (sensorCallback != null)
                    sensorCallback.stepX();
            }
            if (x > 2.0 && carPosition > 0) {
                carPrePosition = carPosition;
                carPosition--;

                if (sensorCallback != null)
                    sensorCallback.stepX();
            }

            if (y < 5.0) {
                delay = 700;
            }
            else{
                delay = 1200;
            }
            if (sensorCallback != null)
                sensorCallback.stepY();

        }
    }



    public int getCarPositionX() {
        return carPosition;
    }


    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

    public int getCarPrePosition() {
        return carPrePosition;
    }

    public void setCarPrePosition(int carPrePosition) {
        this.carPrePosition = carPrePosition;
    }
}


