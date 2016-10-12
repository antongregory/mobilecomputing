package museum.findit.com.myapplication.WebService;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

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
        gamesDatabase.child(gameId).child("status").setValue("open");
    }

    public void start(){

        // TODO: should check whether the number of players is more than 1
        gamesDatabase.child(gameId).child("status").setValue("start");
    }
}
