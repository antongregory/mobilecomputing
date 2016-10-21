package museum.findit.com.myapplication.controller;

import android.util.Log;

import museum.findit.com.myapplication.model.ItemManager;
import museum.findit.com.myapplication.model.ItemModel;
import museum.findit.com.myapplication.model.Question;
import museum.findit.com.myapplication.view.Activities.GameActiviry;

/**
 * Created by antongregory on 20/10/2016.
 */

public class GameController extends Controller{

    ViewHandler viewHandler;
    GameListener gameListener;
    public GameController(ViewHandler handler) {
        super(handler);
        viewHandler=handler;
        gameListener=(GameListener)handler;

    }

    public void startQuiz(){
        Question question=ItemManager.getInstance().loadNextQuestion();
        Log.d("DEBUG"," questiony"+question);
            if (question!=null)
        gameListener.loadQuizItem(question);
        else
        gameListener.onSucess(GameActiviry.class);

    }


    public void updateItem(){
       ItemModel item= ItemManager.getInstance().getItem();
        if(item!=null)
        gameListener.loadGameItem(item);
        else
            gameListener.onFailure();
    }



    public void checkAnswer(String choice){
        String result=ItemManager.getInstance().getCurrentQuestionAnswer();
        Log.d("DEBUG","answer is : "+result);
        Log.d("DEBUG","answer is : "+choice);
        if (null!=result){
            if(result.equals(choice))
            gameListener.highLightCorrect();
            else
                gameListener.highLightWrong(result);
        }
     else {
            Log.d("DEBUG","answer is null to display");
        }

    }

    public interface GameListener extends ViewHandler{
        public void loadGameItem(ItemModel item);
        public void loadQuizItem(Question quiz);
        public void highLightCorrect();
        public void highLightWrong(String answer);
    }
    //load barcode details



}
