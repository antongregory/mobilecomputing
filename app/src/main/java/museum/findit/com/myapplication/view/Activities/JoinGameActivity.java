package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import museum.findit.com.myapplication.R;

public class JoinGameActivity extends AppCompatActivity {

    String message;
    public final static String EXTRA_MESSAGE = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Intent intent = getIntent();
         message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

    }


    public void createGameRoom(View view) {
        Intent intent = new Intent(this, WaitingRoomActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void joinGameRoom(View view) {
        Intent intent = new Intent(this, WaitingRoomActivity.class);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }



}
