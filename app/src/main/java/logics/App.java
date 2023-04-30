package logics;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class App extends Application {

    public void createToast(Context context) {
        Toast.makeText(context,"crash! -5 points",Toast.LENGTH_LONG).show();

    }

    public void createVibrator(Vibrator v){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

    }
}
