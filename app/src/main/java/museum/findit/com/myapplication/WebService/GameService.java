package museum.findit.com.myapplication.WebService;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
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
        DatabaseReference gameDatabase = gamesDatabase.child(gameId);
        gameDatabase.child("status").setValue("opened");
        gameDatabase.child("numberOfPlayers").setValue(1);
    }

    public void start(){

        // TODO: should check whether the number of players is more than 1
        gamesDatabase.child(gameId).child("status").setValue("started");
    }

    public Task<String> join(final String joinGameId){
        final TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        final DatabaseReference statusDatabase = gamesDatabase.child(joinGameId).child("status");
        statusDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStatus = dataSnapshot.getValue(String.class);
                Log.d("GameLog", "Game status is: " + gameStatus);

                if ("opened".equals(gameStatus)) {
                    gameId = joinGameId;
                    gameJoined();
                    taskCompletionSource.setResult(joinGameId);
                } else {
                    taskCompletionSource.setException(new Exception("game already " + gameStatus));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("GameLog", "Failed to read value.", error.toException());
                taskCompletionSource.setException(new Exception("status can't be read"));
            }
        });

        return taskCompletionSource.getTask();
    }

    private void gameJoined(){
        final DatabaseReference numberOfPlayersDatabase = gamesDatabase.child(gameId).child("numberOfPlayers");
        numberOfPlayersDatabase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer numberOfPlayers = mutableData.getValue(Integer.class);
                if (numberOfPlayers == null){
                    return Transaction.success(mutableData);
                }

                numberOfPlayers += 1;
                mutableData.setValue(numberOfPlayers);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("GameLog", "gameJoined:onComplete:" + databaseError);
            }
        });
    }

    public void listenGameStatusChanged(ValueEventListener gameStatusListener){
        final DatabaseReference statusDatabase = gamesDatabase.child(gameId).child("status");
        statusDatabase.addValueEventListener(gameStatusListener);
    }

    public void listenNumberOfPlayers(ValueEventListener numberOfPlayersListener){
        final DatabaseReference numberOfPlayersDatabase = gamesDatabase.child(gameId).child("numberOfPlayers");
        numberOfPlayersDatabase.addValueEventListener(numberOfPlayersListener);
    }

    public void leave(){
        final DatabaseReference numberOfPlayersDatabase = gamesDatabase.child(gameId).child("numberOfPlayers");
        numberOfPlayersDatabase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer numberOfPlayers = mutableData.getValue(Integer.class);
                if (numberOfPlayers == null){
                    return Transaction.success(mutableData);
                }

                numberOfPlayers -= 1;
                mutableData.setValue(numberOfPlayers);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("GameLog", "leave:onComplete:" + databaseError);
            }
        });
    }

    public void cancel(){
        gamesDatabase.child(gameId).child("status").setValue("cancelled");
    }
}
