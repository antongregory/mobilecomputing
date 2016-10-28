package museum.findit.com.myapplication.view.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.controller.LeaderboardUIManager;
import museum.findit.com.myapplication.model.ItemManager;
import museum.findit.com.myapplication.model.Player;

public class EndGameActivity extends AppCompatActivity {


    final LeaderboardUIManager leaderboardUIManager = new LeaderboardUIManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Log.d("GameLog","perce"+ ItemManager.getInstance().getPlayerProfile().getPercentage());
        Log.d("GameLog","gae id "+ GameService.gameId);

        GameService.listenScoreboard(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Player>> objectListType = new GenericTypeIndicator<HashMap<String, Player>>() {};
                HashMap<String, Player> players = dataSnapshot.getValue(objectListType);
                if(players == null) return;
                if(!leaderboardUIManager.isCreated){
                    Context context = getApplicationContext();
                    TableLayout leaderboardLayout = (TableLayout) findViewById(R.id.end_leaderboard);
                    leaderboardUIManager.createLeaderboard(context, leaderboardLayout, players);
                } else {
                    leaderboardUIManager.updateLeaderboard(players);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GameLog", "Players cannot be read: " + databaseError.getDetails());
            }
        });



    }

    public void restartGame(View view){
        Intent i = new Intent(this,JoinGameActivity.class);
        startActivity(i);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

}
