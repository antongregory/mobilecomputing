package museum.findit.com.myapplication.view.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.TimerTask;

import museum.findit.com.myapplication.Adapter.PagerAdapter;
import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.TimerService;

public class GameActiviry extends AppCompatActivity {

    public static final String RECEIVE_TIME = "museum.findit.com.myapplication";
    TextView timeText;

    private BroadcastReceiver bReceiver;
    LocalBroadcastManager bManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Item"));
        tabLayout.addTab(tabLayout.newTab().setText("Leaderboard"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //register receiver
        bReceiver = new timerReceiver();

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_TIME);
        bManager.registerReceiver(bReceiver, intentFilter);

        //start timer service
        startService(new Intent(this, TimerService.class));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, TimerService.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUI(String time) {
        // Do what you need to do

        // it only works here
        timeText = (TextView) findViewById(R.id.timeTextView);
        timeText.setText(time);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void scanBarCode(View view) {

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        integrator.setPrompt("Scan");

        integrator.setCameraId(0);

        integrator.setBeepEnabled(false);

        integrator.setBarcodeImageEnabled(false);
        try {
            integrator.initiateScan();
        } catch (Exception e) {
            Log.d("sad", "sa" + e.toString());
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Intent intent = new Intent(this, QuizActivity.class);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();


                startActivity(intent);//FOR TEST
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();


                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void finish(View view) {
        Intent intent = new Intent(this, EndGameActivity.class);
        startActivity(intent);
    }

    public class timerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RECEIVE_TIME)) {
                String message = intent.getStringExtra("timer");
                updateUI(message);

            }
        }
    }


}
