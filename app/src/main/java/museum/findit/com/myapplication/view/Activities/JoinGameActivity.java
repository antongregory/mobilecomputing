package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;

public class JoinGameActivity extends AppCompatActivity implements Controller.ViewHandler{

    String username;
    public final static String EXTRA_MESSAGE_USERNAME = "user_name";
    public final static String EXTRA_MESSAGE_GAMECODE = "gamecode";
    String gameCode;
    private EditText editText;
    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Intent intent = getIntent();
         username = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE_USERNAME);
        initialise();
        mController=new Controller(this);
    }

    private void initialise(){
        editText=(EditText)findViewById(R.id.gamecodeText);
    }


    public void createGameRoom(View view) {
        String createdGameCode = mController.createGameAction();
        gameCode = createdGameCode;
        onSucess(WaitingRoomActivity.class);
    }

    public void joinGameRoom(View view) {
        gameCode = editText.getText().toString();
        mController.joinGameAction(gameCode);
    }

    public void onSucess(Class view){

        Intent intent = new Intent(this, view);
        intent.putExtra(EXTRA_MESSAGE_USERNAME, username);
        intent.putExtra(EXTRA_MESSAGE_GAMECODE, gameCode);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
