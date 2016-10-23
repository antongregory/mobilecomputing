package museum.findit.com.myapplication.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private Boolean isLeaderboardCreated = false;

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
                if(!isLeaderboardCreated){
                    createLeaderboard(players);
                    isLeaderboardCreated = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GameLog", "Players cannot be read: " + databaseError.getDetails());
            }
        });
    }

    private void createLeaderboard(HashMap<String, Player> players){
        Context context = getContext();
        TableLayout leaderboardLayout = (TableLayout) getActivity().findViewById(R.id.leaderboard);

        for (Map.Entry<String, Player> entry: players.entrySet()) {
            Player player = entry.getValue();
            addTableRow(context, leaderboardLayout, player.username, player.score, player.percentage);
        }
    }

    private HashMap<String, TextView> addTableRow(Context context, TableLayout leaderboardLayout, String username, Integer score, Integer percentage) {
        TableRow row = new TableRow(context);
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        row.setLayoutParams(tableRowParams);
        leaderboardLayout.addView(row);

        TextView usernameTextView = addUsernameTextView(context, row, username);
        TextView scoreTextView = addNumberTextView(context, row, score);
        TextView percentageTextView = addNumberTextView(context, row, percentage);

        HashMap<String, TextView> textViewHashMap = new HashMap<>();
        textViewHashMap.put("username", usernameTextView);
        textViewHashMap.put("score", scoreTextView);
        textViewHashMap.put("percentage", percentageTextView);
        return textViewHashMap;
    }

    private TextView addUsernameTextView(Context context, TableRow row, String username) {
        TextView usernameTextView = new TextView(context);

        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewParams.topMargin = 30;
        usernameTextView.setLayoutParams(textViewParams);

        usernameTextView.setTextColor(Color.parseColor("#ff669900"));
        usernameTextView.setTextSize(18);
        usernameTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        usernameTextView.setFocusable(true);
        usernameTextView.setFocusableInTouchMode(true);
        usernameTextView.setGravity(Gravity.CENTER);
        usernameTextView.setText(username);
        row.addView(usernameTextView);
        return usernameTextView;
    }

    private TextView addNumberTextView(Context context, TableRow row, Integer number) {
        TextView numberTextView = new TextView(context);

        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewParams.topMargin = 30;
        numberTextView.setLayoutParams(textViewParams);

        numberTextView.setTextColor(Color.BLACK);
        numberTextView.setTextSize(18);
        numberTextView.setGravity(Gravity.CENTER);
        numberTextView.setText(number.toString());
        row.addView(numberTextView);
        return numberTextView;
    }
}
