package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.view.Activities.GameActiviry;
import museum.findit.com.myapplication.view.Activities.LoginActivity;

public class WaitingRoomActivity extends AppCompatActivity {

    TextView welcomeInfo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        welcomeInfo.setText("Welcome "+message+"!");

    }

    public void backToLogin(View view) {
       finish();
    }


     public void startGame(View view){
         Intent intent = new Intent(this, GameActiviry.class);
          startActivity(intent);
     }



}
