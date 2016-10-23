package museum.findit.com.myapplication.WebService;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by doniramadhan on 2016-10-12.
 */

public class GameService {
    static DatabaseReference gamesDatabase = FirebaseDatabase.getInstance().getReference("games");

    // TODO: gameId and username should be persistence even when app is turned off
    static String gameId;
    static String username;
    static Integer seed;

    public static void listenNumberOfPlayers(ValueEventListener numberOfPlayersListener){
        final DatabaseReference numberOfPlayersDatabase = gamesDatabase.child(gameId).child("numberOfPlayers");
        numberOfPlayersDatabase.addValueEventListener(numberOfPlayersListener);
    }

    public static void updateScore(Integer score, Integer percentage){
        String userId = LoginService.getId();
        DatabaseReference playerDatabase = gamesDatabase.child(gameId).child("players").child(userId);
        playerDatabase.child("score").setValue(score);
        playerDatabase.child("percentage").setValue(percentage);
    }

    public static void listenScoreboard(ValueEventListener scoreboardListener){
        DatabaseReference playersDatabase = gamesDatabase.child(gameId).child("players");
        playersDatabase.addValueEventListener(scoreboardListener);
    }
}
