package museum.findit.com.myapplication.WebService;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import museum.findit.com.myapplication.Helpers.RandomIdGenerator;

/**
 * Created by doniramadhan on 2016-10-12.
 */

public class GameService {
    private static GameService singleton = null;
    private GameService() {  }
    public static GameService shared() {
        if (singleton == null) {
            singleton = new GameService();
        }
        return singleton;
    }

    private DatabaseReference gamesDatabase = FirebaseDatabase.getInstance().getReference("games");

    // TODO: gameId should be persistence even when app is turned off
    private String gameId;

    public void create(){
        gameId = RandomIdGenerator.GetBase36(6);
        gamesDatabase.child(gameId).child("status").setValue("opened");
    }

    public void start(){

        // TODO: should check whether the number of players is more than 1
        gamesDatabase.child(gameId).child("status").setValue("started");
    }

    public Task<String> join(final String joinGameId){
        final TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        final DatabaseReference statusDatabase = gamesDatabase.child(joinGameId).child("status");
        statusDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStatus = dataSnapshot.getValue(String.class);
                Log.d("GameLog", "Game status is: " + gameStatus);

                if ("opened".equals(gameStatus)) {
                    gameId = joinGameId;
                    taskCompletionSource.setResult(joinGameId);
                } else {
                    statusDatabase.removeEventListener(this);
                    taskCompletionSource.setException(new Exception("game already " + gameStatus));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameLog", "Failed to read value.", error.toException());
                statusDatabase.removeEventListener(this);
                taskCompletionSource.setException(new Exception("status can't be read"));
            }
        });

        return taskCompletionSource.getTask();
    }
}
