package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import museum.findit.com.myapplication.R;

public class JoinGameActivity extends AppCompatActivity {

    String message;
    public final static String EXTRA_MESSAGE_USERNAME = "user_name";
    public final static String EXTRA_MESSAGE_GAMECODE = "gamecode";
    String gameCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Intent intent = getIntent();
         message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE_NAME);

    }


    public void createGameRoom(View view) {
        Intent intent = new Intent(this, WaitingRoomActivity.class);
        intent.putExtra(EXTRA_MESSAGE_USERNAME, message);

        startActivity(intent);
    }

    public void joinGameRoom(View view) {
        gameCode= ((EditText) findViewById(R.id.gamecodeText)).getText().toString();

        Intent intent = new Intent(this, WaitingRoomActivity.class);

        intent.putExtra(EXTRA_MESSAGE_USERNAME, message);
        intent.putExtra(EXTRA_MESSAGE_GAMECODE, gameCode);

        startActivity(intent);
    }



}
