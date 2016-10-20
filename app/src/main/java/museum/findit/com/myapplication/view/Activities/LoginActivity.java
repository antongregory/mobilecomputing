package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import museum.findit.com.myapplication.R;


public class LoginActivity extends AppCompatActivity {


    public final static String EXTRA_MESSAGE_NAME = "user_name";
    EditText userName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.gamecode);
    }

    public void enterWaitingRoom(View view) {

        Intent intent = new Intent(this, JoinGameActivity.class);
        String name = userName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE_NAME, name);
        startActivity(intent);
    }
}
