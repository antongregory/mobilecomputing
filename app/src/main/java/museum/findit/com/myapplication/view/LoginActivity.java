package museum.findit.com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.WebService.LoginService;

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
        final Intent intent = new Intent(this, WaitingRoomActivity.class);
        final String name = userName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        LoginService.shared().login().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("UserLog", "signInAnonymously:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w("UserLog", "signInAnonymously", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
//                    GameService.shared().create();
                    GameService.join(name).addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (task.isSuccessful()){
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Join game failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
