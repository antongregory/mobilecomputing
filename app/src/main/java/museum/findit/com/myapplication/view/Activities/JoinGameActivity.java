package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import museum.findit.com.myapplication.Helpers.MyApplication;
import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.Controller;

public class JoinGameActivity extends AppCompatActivity implements Controller.ViewHandler{

    String username;
    String gameCode;
    private Controller mController;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        welcome = (TextView) findViewById(R.id.welcomeTxt);

         username = ((MyApplication) this.getApplication()).getUserName();

        mController=new Controller(this);
    }



    public void createGameRoom(View view) {
        String createdGameCode = mController.createGameAction();
        gameCode = createdGameCode;
        ((MyApplication) this.getApplication()).setOwner(true);
        ((MyApplication) this.getApplication()).setGameCode(gameCode);
        onSucess(WaitingRoomActivity.class);
    }

    public void joinGameRoom(View view) {

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
            }
            else {

                String code = result.getContents();
                ((MyApplication) this.getApplication()).setOwner(false);

                ((MyApplication) this.getApplication()).setGameCode(code);
                startGame(code);

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
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
