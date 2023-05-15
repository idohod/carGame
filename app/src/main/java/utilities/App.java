package utilities;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class App extends Application {

    public void onCreate() {
        super.onCreate();

        MySharedPreferences.init(this);
        SignalGenerator.init(this);

    }
    public void createToast(Context context) {
        Toast.makeText(context,"crash! -5 points",Toast.LENGTH_SHORT).show();

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
