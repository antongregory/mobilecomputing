package museum.findit.com.myapplication.view.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import museum.findit.com.myapplication.Helpers.MyApplication;
import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.WebService.GameOwnerService;
import museum.findit.com.myapplication.WebService.GameParticipantService;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.controller.Controller;
import museum.findit.com.myapplication.model.CurrentUser;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class WaitingRoomActivity extends AppCompatActivity implements Controller.ViewHandler {

    TextView welcomeInfo ;
    String gamecode;
    private Controller mController;
    String message;
    boolean owner;
    Button startBtn;
    Button leaveBtn;
    LinearLayout btnslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
         gamecode = ((MyApplication) this.getApplication()).getGameCode();
        owner = ((MyApplication) this.getApplication()).isOwner();
         btnslayout  = (LinearLayout) findViewById(R.id.buttonsLayout);
         startBtn = (Button) findViewById(R.id.startbtn);
        leaveBtn = (Button) findViewById(R.id.leaveBtn);

        if(CurrentUser.isParticipant()){

            btnslayout.removeView(startBtn);
            leaveBtn.getLayoutParams().height+=leaveBtn.getLayoutParams().height;
            GameParticipantService.getGameStatus().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String gameStatus = dataSnapshot.getValue(String.class);
                    switch (gameStatus){
                        case "started":
                            mController.startLoadingData();
                            break;
                        case "cancelled":
                            finish();
                            break;
                        default: break;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("GameLog", "Game status cannot be read: " + databaseError.getDetails());
                }
            });
        }

        numberOfPlayersUpdateIfNeeded();


        if(owner){
            initialise(gamecode);
        }else{
            initialise();
        }

        message = ((MyApplication) this.getApplication()).getUserName();

        welcomeInfo = (TextView) findViewById(R.id.welcomeTxt);
        welcomeInfo.setText("Welcome "+message+"!");

        mController=new Controller(this);

    }

    private void numberOfPlayersUpdateIfNeeded() {
        final TextView numberOfPlayersTextView = (TextView) findViewById(R.id.numberOfPlayers);
        GameService.listenNumberOfPlayers(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer numberOfplayers = dataSnapshot.getValue(Integer.class);
                if(numberOfplayers != null)
                    numberOfPlayersTextView.setText(numberOfplayers.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GameLog", "Number of players cannot be read: " + databaseError.getDetails());
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initialise(){
        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        imageView.setVisibility(View.INVISIBLE);
        TextView description = (TextView) findViewById(R.id.info);
        description.setText("");
        description.setVisibility(View.INVISIBLE);
    }

    private void initialise(String code){

        ImageView imageView1 = (ImageView) findViewById(R.id.qrCode);
        imageView1.setVisibility(View.VISIBLE);
        TextView description = (TextView) findViewById(R.id.info);
        description.setText(R.string.waitingInfo);
        description.setVisibility(View.VISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        try {
            Bitmap bitmap = encodeAsBitmap(code);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) startBtn.getLayoutParams();
        layoutParams.rightMargin += 15;

        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) startBtn.getLayoutParams();
        layoutParams1.leftMargin += 15;

        startBtn.setLayoutParams(layoutParams);
        leaveBtn.setLayoutParams(layoutParams1);



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
        if(CurrentUser.isOwner()){
            GameOwnerService.cancel();
        } else {
            GameParticipantService.leave();
        }

       finish();
    }


     public void startGame(View view){
         if(CurrentUser.isOwner()){
             GameOwnerService.start();
             mController.startLoadingData();
         }
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
}
