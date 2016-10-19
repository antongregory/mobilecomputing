package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import museum.findit.com.myapplication.R;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
    }

    public void restartGame(View view){
        Intent i = new Intent(this,JoinGameActivity.class);
        startActivity(i);
    }
}
