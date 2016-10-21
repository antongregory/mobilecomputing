package museum.findit.com.myapplication.view.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;


public class LoginActivity extends AppCompatActivity implements Controller.ViewHandler{


    public final static String EXTRA_MESSAGE_USERNAME = "user_name";
    EditText userName ;
    String name;
    private Controller mController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.gamecode);
        mController=new Controller(this);
    }

    public void enterWaitingRoom(View view) {
         name = userName.getText().toString();
        mController.loginAction(name);

    }

    @Override
    public void onSucess(Class view) {
        Intent intent = new Intent(this, view);
        intent.putExtra(EXTRA_MESSAGE_USERNAME, name);
        startActivity(intent);
    }

    @Override
    public void onFailure() {

        Toast.makeText(this, "User name cannot be empty", Toast.LENGTH_LONG).show();
    }
}
