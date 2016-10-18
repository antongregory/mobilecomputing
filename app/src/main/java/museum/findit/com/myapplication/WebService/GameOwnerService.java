package museum.findit.com.myapplication.WebService;

import com.google.firebase.database.DatabaseReference;

import museum.findit.com.myapplication.Helpers.RandomIdGenerator;

/**
 * Created by doniramadhan on 2016-10-12.
 */
public class GameOwnerService extends GameService {
    public static void start(){

        // TODO: should check whether the number of players is more than 1
        gamesDatabase.child(gameId).child("status").setValue("started");
    }

    public static void cancel(){
        gamesDatabase.child(gameId).child("status").setValue("cancelled");
    }

    public void create(){
        gameId = RandomIdGenerator.GetBase36(6);
        DatabaseReference gameDatabase = gamesDatabase.child(gameId);
        gameDatabase.child("status").setValue("opened");
        gameDatabase.child("numberOfPlayers").setValue(1);
    }
}
