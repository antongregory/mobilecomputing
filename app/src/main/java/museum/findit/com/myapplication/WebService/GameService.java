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

    public void create(){
        String gameId = RandomIdGenerator.GetBase36(6);
        gamesDatabase.child(gameId).child("status").setValue("open");
    }
}
