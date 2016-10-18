package museum.findit.com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import museum.findit.com.myapplication.R;


public class LoginActivity extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    EditText userName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.username);
    }

    public void enterWaitingRoom(View view) {
        final Intent intent = new Intent(this, InitialGameActiviry.class);
        startActivity(intent);
    }
}
