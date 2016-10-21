package museum.findit.com.myapplication.view.Activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.QuizTimerService;
import museum.findit.com.myapplication.controller.TimerService;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;
import museum.findit.com.myapplication.controller.GameController;
import museum.findit.com.myapplication.model.ItemModel;
import museum.findit.com.myapplication.model.Question;

public class QuizActivity extends AppCompatActivity implements GameController.GameListener{

    private BroadcastReceiver bReceiver;
    LocalBroadcastManager bManager;
    public static final String RECEIVE_TIME = "museum.findit.com.myapplication.quiz";

    TextView quizTimerTextView;



    TextView questionNoTextView;
    TextView scoreTextView;
    TextView questionTextView;
    Button choiceA;
    Button choiceB;
    Button choiceC;
    Button selectedChoice,correctChoice;
    private GameController mController;
    private   Animation mAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initialise();
        mController=new GameController(this);
        mController.startQuiz();
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
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initialise(){
        questionNoTextView=(TextView)findViewById(R.id.questionNoTextView);
        scoreTextView=(TextView)findViewById(R.id.scoreTextView);
        questionTextView=(TextView)findViewById(R.id.questionTextView);
        choiceA=(Button) findViewById(R.id.choiceA);
        choiceB=(Button) findViewById(R.id.choiceB);
        choiceC=(Button) findViewById(R.id.choiceC);
        initAnimation();

    }

    private void  initAnimation(){
         mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
    }
    private void disableButton(){
        choiceA.setEnabled(false);
        choiceB.setEnabled(false);
        choiceC.setEnabled(false);

    }

    public void selectedChoice(View view){

        selectedChoice = (Button)view.findViewById(view.getId());
        String choice = selectedChoice.getText().toString();
       mController.checkAnswer(choice);


    }


    @Override
    public void onSucess(Class view) {
        Intent intent = new Intent(this, view);

        startActivity(intent);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void loadGameItem(ItemModel item) {


    }

    @Override
    public void loadQuizItem(Question quiz) {

        questionTextView.setText(quiz.getQuestion());
        choiceA.setText(quiz.getChoiceA());
        choiceB.setText(quiz.getChoiceB());
        choiceC.setText(quiz.getChoiceC());

        Log.d("DEBUG","loading item "+quiz);
    }

    @Override
    public void highLightCorrect() {
        Toast.makeText(this, "right choice"+selectedChoice.getText(),Toast.LENGTH_LONG).show();
        finishQuiz();
    }

    @Override
    public void highLightWrong(String answer) {

        int buttonId=findRightButton( answer);
        switch(buttonId) {
            case R.id.choiceA:
                choiceA.startAnimation(mAnimation);
                Toast.makeText(this, "right choice a",Toast.LENGTH_LONG).show();

                break;
            case R.id.choiceB:
                choiceB.startAnimation(mAnimation);
                Toast.makeText(this, "right choice b",Toast.LENGTH_LONG).show();

                break;
            case R.id.choiceC:
                choiceC.startAnimation(mAnimation);
                Toast.makeText(this, "right choice c",Toast.LENGTH_LONG).show();

                break;
        }

        Toast.makeText(this, "wrong "+selectedChoice.getText(),Toast.LENGTH_LONG).show();
        finishQuiz();
    }


    private void finishQuiz(){
        final Handler handler = new Handler();
        Log.d("DEBUG","handler");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             mController.startQuiz();
            }
        }, 1500);
    }
    private int findRightButton(String answer){

        if(choiceA.getText().toString().equals(answer)){

            return choiceA.getId();
        }

        else if(choiceB.getText().toString().equals(answer)){

            return choiceB.getId();
        }
        else{

            return choiceC.getId();
        }


    }


}
