package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.TimerService;
import museum.findit.com.myapplication.controller.Controller;
import museum.findit.com.myapplication.view.Activities.GameActiviry;
import museum.findit.com.myapplication.view.Activities.LoginActivity;

import static android.R.attr.width;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class WaitingRoomActivity extends AppCompatActivity implements Controller.ViewHandler {

    TextView welcomeInfo ;

    String gamecode;
    TextView gamecodeTextView;
    private Controller mController;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
        gamecodeTextView =  (TextView) findViewById(R.id.gamecode);

        Intent intent = getIntent();
        String name = intent.getStringExtra(JoinGameActivity.EXTRA_MESSAGE_USERNAME);
         gamecode = intent.getStringExtra(JoinGameActivity.EXTRA_MESSAGE_GAMECODE);
        if(gamecode!=null&&!gamecode.equals("")){
            gamecodeTextView.setText(gamecode);
        }else{

        }


        initialise("hahahaha");
        mController=new Controller(this);

        welcomeInfo.setText("Welcome "+name+"!");

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initialise(String code){
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
        welcomeInfo.setText("Welcome "+message+"!");

        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        try {
            Bitmap bitmap = encodeAsBitmap(code);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    public void backToLogin(View view) {
       finish();
    }


     public void startGame(View view){
            mController.startLoadingData();
     }


    @Override
    public void onSucess(Class view) {
        Intent intent = new Intent(this, view);

        startActivity(intent);
    }

    @Override
    public void onFailure() {

    }
}
