package museum.findit.com.myapplication.WebService;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by doniramadhan on 2016-10-12.
 */
public class GameParticipantService extends GameService{
    public static Task<String> join(final String joinGameId){
        final TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        final DatabaseReference statusDatabase = GameService.gamesDatabase.child(joinGameId).child("status");
        statusDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStatus = dataSnapshot.getValue(String.class);
                Log.d("GameLog", "Game status is: " + gameStatus);

                if ("opened".equals(gameStatus)) {
                    GameService.gameId = joinGameId;
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

    private static void gameJoined(){
        final DatabaseReference numberOfPlayersDatabase = GameService.gamesDatabase.child(GameService.gameId).child("numberOfPlayers");
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

    public static void listenGameStatusChanged(ValueEventListener gameStatusListener){
        final DatabaseReference statusDatabase = GameService.gamesDatabase.child(GameService.gameId).child("status");
        statusDatabase.addValueEventListener(gameStatusListener);
    }

    public static void leave(){
        final DatabaseReference numberOfPlayersDatabase = GameService.gamesDatabase.child(GameService.gameId).child("numberOfPlayers");
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
}
