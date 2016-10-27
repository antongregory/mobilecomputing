package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import museum.findit.com.myapplication.Helpers.MyApplication;
import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;


public class LoginActivity extends AppCompatActivity implements Controller.ViewHandler, Animation.AnimationListener {


    EditText userName ;
    String name;
    private Controller mController;
    static int IMAGE_NUMBER = 1;

    RelativeLayout SpalshScreen_rLayout;
    Animation animZoomIn, fadeIn, fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.gamecode);
        mController=new Controller(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initializing all utilities
        SpalshScreen_rLayout = (RelativeLayout) findViewById(R.id.rLayout_Spalsh);

        animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // listeners for animation effects..
        animZoomIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        fadeIn.setAnimationListener(this);

        // Setting RelativeLayout to zoom animation at starting
        SpalshScreen_rLayout.startAnimation(animZoomIn);



    }

    public void enterWaitingRoom(View view) {
         name = userName.getText().toString();
        ((MyApplication) this.getApplication()).setUsername(name);
        mController.loginAction(name);

    }

    @Override
    public void onSucess(Class view) {
        Intent intent = new Intent(this, view);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animZoomIn) {
            SpalshScreen_rLayout.startAnimation(fadeOut);
            Log.d("DEBUG","animZoomIn");
        } else if (animation == fadeOut) {
            if (IMAGE_NUMBER == 1) {
                IMAGE_NUMBER = 2;
                SpalshScreen_rLayout.setBackgroundResource(R.drawable.background_two);
                SpalshScreen_rLayout.startAnimation(animZoomIn);
            } else if (IMAGE_NUMBER == 2) {
                IMAGE_NUMBER = 3;
                SpalshScreen_rLayout.setBackgroundResource(R.drawable.background_three);
                SpalshScreen_rLayout.startAnimation(animZoomIn);
            } else {
                IMAGE_NUMBER = 1;
                SpalshScreen_rLayout.setBackgroundResource(R.drawable.background_one);
                SpalshScreen_rLayout.startAnimation(animZoomIn);
            }

        } else if (animation == fadeIn) {
            SpalshScreen_rLayout.startAnimation(animZoomIn);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
