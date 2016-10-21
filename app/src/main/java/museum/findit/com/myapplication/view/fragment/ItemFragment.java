package museum.findit.com.myapplication.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.w3c.dom.Text;

import museum.findit.com.myapplication.R;
import museum.findit.com.myapplication.controller.GameController;
import museum.findit.com.myapplication.model.ItemManager;
import museum.findit.com.myapplication.model.ItemModel;
import museum.findit.com.myapplication.model.Question;

import static museum.findit.com.myapplication.R.id.itemTextView;
import static museum.findit.com.myapplication.R.id.scoreTextView;

/**
 * Created by hui on 2016-10-06.
 */

public class ItemFragment extends Fragment  implements GameController.GameListener{

    TextView itemTextView;
    TextView scoreTextView;
    TextView timeTextView;
    ImageView imageView;
    ItemModel item;
    private GameController gameController;


    public ItemFragment(){
        gameController=new GameController(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_item, container, false);
        intialise(view);
        gameController.updateItem();
        itemTextView.setText("2/5");


        return view;
    }

    private void intialise(View view){
        itemTextView=(TextView) view.findViewById(R.id.itemTextView);
        scoreTextView=(TextView) view.findViewById(R.id.scoreTextView);
        imageView=(ImageView) view.findViewById(R.id.itemImageView);
    }

    private void loadData(){

        itemTextView.setText(item.getBarcodeId());

    }


    @Override
    public void loadNextView(Class view) {

    }

    @Override
    public void displayFailMessage() {
        Toast.makeText(getActivity(), "Load end game screen", Toast.LENGTH_LONG).show();

    }

    @Override
    public void loadGameItem(ItemModel item) {
        Log.d("DEBUG","loading item "+item);

    }

    @Override
    public void loadQuizItem(Question quiz) {

    }

    @Override
    public void highLightCorrect() {

    }

    @Override
    public void highLightWrong(String answer) {

    }
}
