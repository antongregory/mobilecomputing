package museum.findit.com.myapplication.view.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
import android.view.KeyEvent;
>>>>>>> develop
import android.view.View;
import android.widget.TextView;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.QuizTimerService;
import museum.findit.com.myapplication.controller.TimerService;

public class QuizActivity extends AppCompatActivity {

    private BroadcastReceiver bReceiver;
    LocalBroadcastManager bManager;
    public static final String RECEIVE_TIME = "museum.findit.com.myapplication.quiz";

    TextView quizTimerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizTimerTextView = (TextView) findViewById(R.id.timeTextViewQuiz);
        //register receiver
        bReceiver = new quizTimerReceiver();

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_TIME);
        bManager.registerReceiver(bReceiver, intentFilter);

        //start timer service
        startService(new Intent(this, QuizTimerService.class));

    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, QuizTimerService.class));
        super.onDestroy();

    }


    public void showDescription(View vew) {
        stopService(new Intent(this, QuizTimerService.class));
        String descriptionExp = "A course or document may need to use an image which would need a fairly long description to properly describe the content. In this case the recommendation is to include a short summary in the ALT tag which points to or links blind users to a long text description which fully explains the image.\n" +
                "\n" +
                "In most cases, the long description should be available to all users. This long description will not only assist blind users but also sighted users who may not understand a complex image.\n" +
                "\n" +
                "The examples below will provide examples of long description and how to place them to benefit the most users.";

        new AlertDialog.Builder(this)
                .setTitle("//Item Name")
                .setMessage(descriptionExp)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finish();
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }


    private void updateUI(String time) {
        quizTimerTextView.setText(time);

    }

    public class quizTimerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RECEIVE_TIME)) {
                String message = intent.getStringExtra("Quiztimer");
                //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Log.v("QuizTimer:", message);
                updateUI(message);
            }
        }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);


    }
}
