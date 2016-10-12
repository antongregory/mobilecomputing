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
        GameService.shared().listenStarted().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    // TODO: move to game screen
                } else {
                    Toast.makeText(WaitingRoomActivity.this, "Game is cancelled", Toast.LENGTH_SHORT).show();
                    // TODO: move to login screen
                }
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
       finish();
    }


     public void startGame(View view){
         Intent intent = new Intent(this, GameActiviry.class);

         GameService.shared().start();
         startActivity(intent);
     }



}
