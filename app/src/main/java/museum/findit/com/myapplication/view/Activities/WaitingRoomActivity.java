package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;

public class WaitingRoomActivity extends AppCompatActivity implements Controller.ViewHandler {

    TextView welcomeInfo ;

    String gamecode;
    TextView gamecodeTextView;
    private Controller mController;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
        gamecodeTextView =  (TextView) findViewById(R.id.gamecode);

        Intent intent = getIntent();
        String name = intent.getStringExtra(JoinGameActivity.EXTRA_MESSAGE_USERNAME);
         gamecode = intent.getStringExtra(JoinGameActivity.EXTRA_MESSAGE_GAMECODE);
        if(gamecode!=null&&!gamecode.equals("")){
            gamecodeTextView.setText(gamecode);
        }else{

        }


        initialise();
        mController=new Controller(this);

        welcomeInfo.setText("Welcome "+name+"!");

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
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
        welcomeInfo.setText("Welcome "+message+"!");
    }
    public void backToLogin(View view) {
       finish();
    }


     public void startGame(View view){
            mController.startLoadingData();
     }


    @Override
    public void onSucess(Class view) {
        Intent intent = new Intent(this, view);

        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {

    }
}
