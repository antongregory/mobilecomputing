package museum.findit.com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameService;

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
        GameService.shared().listenGameStatusChanged(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStatus = dataSnapshot.getValue(String.class);

                if("started".equals(gameStatus)){
                    startGame();
                } else if ("cancelled".equals(gameStatus)){
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("GameLog","Failed to read value.", error.toException());
            }
        });

        GameService.shared().listenNumberOfPlayers(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Integer numberOfPlayers = dataSnapshot.getValue(Integer.class);
                Log.d("GameLog", "Number of players is: " + numberOfPlayers);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameLog", "Failed to read value.", error.toException());
            }
        });
    }

    public void backToLogin(View view) {
        GameService.shared().leave();
       finish();
    }


     public void startGame(View view){
         GameService.shared().start();
         startGame();
     }

    private void startGame(){
        Intent intent = new Intent(this, GameActiviry.class);
        startActivity(intent);
    }

}
