package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;

public class JoinGameActivity extends AppCompatActivity implements Controller.ViewHandler{

    String username;
    public final static String EXTRA_MESSAGE_USERNAME = "user_name";
    public final static String EXTRA_MESSAGE_GAMECODE = "gamecode";

    String gameCode;
    private EditText editText;


    private Controller mController;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        welcome = (TextView) findViewById(R.id.welcomeTxt);
        Intent intent = getIntent();

         username = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE_USERNAME);
        initialise();

        mController=new Controller(this);
    }



    public void createGameRoom(View view) {
        String createdGameCode = mController.createGameAction();
        gameCode = createdGameCode;
        onSucess(WaitingRoomActivity.class);
    }

    public void joinGameRoom(View view) {

        gameCode = editText.getText().toString();
        mController.joinGameAction(gameCode);

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        integrator.setPrompt("Scan");

        integrator.setCameraId(0);

        integrator.setBeepEnabled(false);

        integrator.setBarcodeImageEnabled(false);
        try{
            integrator.initiateScan();
        }
        catch (Exception e){
            Log.d("Exception","sa"+e.toString());
        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                startGame(result.getContents());//test

            }
            else {

                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                startGame(result.getContents());

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startGame(String code){
        mController.joinGameAction(code);
    }

    public void onSucess(Class view){

        Intent intent = new Intent(this, view);
        intent.putExtra(EXTRA_MESSAGE_USERNAME, username);
        intent.putExtra(EXTRA_MESSAGE_GAMECODE, gameCode);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
