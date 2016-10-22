package museum.findit.com.myapplication.WebService;

import com.google.firebase.database.DatabaseReference;

import museum.findit.com.myapplication.Helpers.RandomIdGenerator;

/**
 * Created by doniramadhan on 2016-10-12.
 */
public class GameOwnerService extends GameService {
    public static void start(){
        gamesDatabase.child(gameId).child("status").setValue("started");
    }

    public static void cancel(){
        gamesDatabase.child(gameId).child("status").setValue("cancelled");
    }

    public static String create(String username){
        GameService.username = username;

        String userId = LoginService.getId();
        gameId = RandomIdGenerator.GetBase36(6);
        DatabaseReference gameDatabase = gamesDatabase.child(gameId);
        gameDatabase.child("status").setValue("opened");
        gameDatabase.child("numberOfPlayers").setValue(1);

        DatabaseReference playerDatabase = gameDatabase.child("players").child(userId);
        playerDatabase.child("username").setValue(username);
        playerDatabase.child("order").setValue(1);
        playerDatabase.child("percentage").setValue(0);
        playerDatabase.child("score").setValue(0);

        return gameId;
    }
}
