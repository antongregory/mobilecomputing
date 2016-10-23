package museum.findit.com.myapplication.view.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.controller.LeaderboardUIManager;
import museum.findit.com.myapplication.model.Player;


/**
 * Created by hui on 2016-10-06.
 */

public class LeaderboardFragment extends Fragment {

    final LeaderboardUIManager leaderboardUIManager = new LeaderboardUIManager();

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
                if(!leaderboardUIManager.isCreated){
                    Context context = getContext();
                    TableLayout leaderboardLayout = (TableLayout) getActivity().findViewById(R.id.leaderboard);
                    leaderboardUIManager.createLeaderboard(context, leaderboardLayout, players);
                } else {
                    leaderboardUIManager.updateLeaderboard(players);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GameLog", "Players cannot be read: " + databaseError.getDetails());
            }
        });
    }
}
