package museum.findit.com.myapplication.WebService;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by doniramadhan on 2016-10-12.
 */

public class GameService {
    static DatabaseReference gamesDatabase = FirebaseDatabase.getInstance().getReference("games");

    // TODO: gameId and username should be persistence even when app is turned off
    static String gameId;
    static String username;

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
}
