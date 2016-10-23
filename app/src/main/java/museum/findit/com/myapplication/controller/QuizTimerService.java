package museum.findit.com.myapplication.controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import museum.findit.com.myapplication.view.Activities.GameActiviry;
import museum.findit.com.myapplication.view.Activities.QuizActivity;

public class QuizTimerService extends Service {
    private static Timer timer;
    private Context ctx;
    private int seconds= 0;

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
    }

    private void startService()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new QuizTimerService.mainTask(), 0, 1000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            seconds++;
            Log.v("Quiz timer",""+seconds);
            broadcastIntent();

            //     Toast.makeText(ctx, "test", Toast.LENGTH_SHORT).show();
        }
    }
    public void broadcastIntent(){
        Intent intent = new Intent(QuizActivity.RECEIVE_TIME);
        intent.putExtra("Quiztimer",timerConverter(seconds));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onDestroy()
    {

        super.onDestroy();
        timer.cancel();

        //  Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }
    public String timerConverter(int sec){
        int minutes = (sec % 3600) / 60;
        int seconds = sec % 60;

        return String.format("%02d:%02d", minutes, seconds);


    }
}
