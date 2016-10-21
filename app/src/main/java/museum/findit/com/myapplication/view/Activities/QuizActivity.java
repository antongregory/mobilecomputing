package museum.findit.com.myapplication.view.Activities;

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
    public void loadNextView(Class view) {
        Intent intent = new Intent(this, view);

        startActivity(intent);
    }

    @Override
    public void displayFailMessage() {

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
        Toast.makeText(this, "right choice a",Toast.LENGTH_LONG).show();
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
