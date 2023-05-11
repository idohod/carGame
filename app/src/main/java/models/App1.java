package models;

import android.app.Application;

import utilities.MySP;
import utilities.SignalGenerator;

public class App1 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
        SignalGenerator.init(this);
    }
}
