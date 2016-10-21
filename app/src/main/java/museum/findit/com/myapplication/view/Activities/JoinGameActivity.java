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

    String message;
    public final static String EXTRA_MESSAGE_USERNAME = "user_name";
    public final static String EXTRA_MESSAGE_GAMECODE = "gamecode";
    String gameCode;
    public final static String EXTRA_MESSAGE = "name";
    private EditText editText;
    private Controller mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Intent intent = getIntent();
         message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE_USERNAME);
        initialise();
        mController=new Controller(this);
    }

    private void initialise(){
        editText=(EditText)findViewById(R.id.gamecodeText);
    }


    public void createGameRoom(View view) {
        mController.createGameAction();
    }

    public void joinGameRoom(View view) {
        mController.joinGameAction(editText.getText().toString());
    }

    public void onSucess(Class view){

        Intent intent = new Intent(this, view);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
