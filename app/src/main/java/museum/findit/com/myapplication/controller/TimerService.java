package museum.findit.com.myapplication.controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import museum.findit.com.myapplication.view.Activities.GameActiviry;

public class TimerService extends Service {
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
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }


    private class mainTask extends TimerTask
    {
        public void run()
        {
            seconds++;
           Log.v("timer",""+seconds);
            broadcastIntent();

       //     Toast.makeText(ctx, "test", Toast.LENGTH_SHORT).show();
        }
    }
    public void broadcastIntent(){
        Intent intent = new Intent(GameActiviry.RECEIVE_TIME);
        intent.putExtra("timer",timerConverter(seconds));
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
