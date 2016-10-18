package museum.findit.com.myapplication.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import museum.findit.com.myapplication.R;

public class InitialGameActiviry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_game);
    }

    public void createGame(View view){
        //TODO

        final Intent intent = new Intent(this, WaitingRoomActivity.class);
        startActivity(intent);
    }
    public void enterGame(View view){
        //TODO

        final Intent intent = new Intent(this, WaitingRoomActivity.class);
        startActivity(intent);
    }
}
