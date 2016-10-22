package museum.findit.com.myapplication.view.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.model.Player;


/**
 * Created by hui on 2016-10-06.
 */

public class LeaderboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GameService.listenScoreboard(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Player>> objectListType = new GenericTypeIndicator<HashMap<String, Player>>() {};
                HashMap<String, Player> players = dataSnapshot.getValue(objectListType);
                if(players == null) return;
                for (Map.Entry<String, Player> entry: players.entrySet()) {
                    Player player = entry.getValue();
                    // TODO: 2016-10-23 use player to build scoreboard
                    Log.d("GameLeaderBoard", player.username + "|" + player.score.toString() + "|" + player.percentage.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GameLog", "Players cannot be read: " + databaseError.getDetails());
            }
        });
    }
}
