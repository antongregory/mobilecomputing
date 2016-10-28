package museum.findit.com.myapplication.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import museum.findit.com.myapplication.model.Player;

public class LeaderboardUIManager {
    public Boolean isCreated = false;
    private HashMap<String, HashMap<String, TextView>> leaderboardHashMap;

    public LeaderboardUIManager() {
    }

    public void updateLeaderboard(HashMap<String, Player> players) {
        if (leaderboardHashMap == null) return;
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            HashMap<String, TextView> rowHashMap = leaderboardHashMap.get(entry.getKey());
            updateRow(rowHashMap, player);
        }
    }

    private void updateRow(HashMap<String, TextView> rowHashMap, Player player) {
        if (rowHashMap == null) return;
        TextView scoreTextView = rowHashMap.get("score");
        TextView percentageTextView = rowHashMap.get("percentage");

        if (scoreTextView == null || percentageTextView == null ||
                player.score == null || player.percentage == null) return;
        scoreTextView.setText(player.score.toString());
        percentageTextView.setText(player.percentage.toString() + "%");
    }

    public void createLeaderboard(Context context, TableLayout leaderboardLayout, HashMap<String, Player> players) {
        HashMap<String, HashMap<String, TextView>> leaderboardHashMap = new HashMap<String, HashMap<String, TextView>>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            HashMap<String, TextView> rowHashMap = addTableRow(context, leaderboardLayout, player);
            leaderboardHashMap.put(entry.getKey(), rowHashMap);
        }
        this.leaderboardHashMap = leaderboardHashMap;
        this.isCreated = true;
    }

    private HashMap<String, TextView> addTableRow(Context context, TableLayout leaderboardLayout, Player player) {
        TableRow row = new TableRow(context);
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        row.setLayoutParams(tableRowParams);
        leaderboardLayout.addView(row);

        if(player.username == null || player.score == null || player.percentage == null) return null;
        addUsernameTextView(context, row, player.username);
        TextView scoreTextView = addNumberTextView(context, row, player.score, false);
        TextView percentageTextView = addNumberTextView(context, row, player.percentage, true);

        HashMap<String, TextView> textViewHashMap = new HashMap<String, TextView>();
        textViewHashMap.put("score", scoreTextView);
        textViewHashMap.put("percentage", percentageTextView);
        return textViewHashMap;
    }

    private void addUsernameTextView(Context context, TableRow row, String username) {
        TextView usernameTextView = createDefaultTextView(context);
        highlightTextView(usernameTextView);

        usernameTextView.setText(username);
        usernameTextView.setTextColor(Color.parseColor("#FFFF8800"));
        row.addView(usernameTextView);
    }

    private TextView addNumberTextView(Context context, TableRow row, Integer number, Boolean withPercentage) {
        TextView numberTextView = createDefaultTextView(context);

        String text = number.toString();
        if(withPercentage) text += "%";
        numberTextView.setText(text);

        row.addView(numberTextView);
        return numberTextView;
    }

    @NonNull
    private TextView createDefaultTextView(Context context) {
        TextView textView = new TextView(context);

        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewParams.topMargin = 30;
        textView.setLayoutParams(textViewParams);

        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private void highlightTextView(TextView textView) {
        textView.setTextColor(Color.parseColor("#ff669900"));
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
    }
}