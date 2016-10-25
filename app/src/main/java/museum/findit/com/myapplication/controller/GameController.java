package museum.findit.com.myapplication.controller;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import museum.findit.com.myapplication.Helpers.GameScore;
import museum.findit.com.myapplication.Helpers.TimeUtils;
import museum.findit.com.myapplication.WebService.GameService;
import museum.findit.com.myapplication.WebService.ImageService;
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
            if (question!=null){
                gameListener.loadQuizItem(question);
                gameListener.updateScoreView((int) ItemManager.getInstance().getCurrentScore());
            }

        else{
                double score=ItemManager.getInstance().getCurrentScore();
                int percentage=ItemManager.getInstance().getPercentage();
                GameService.updateScore((int) score,percentage);
                gameListener.onSucess(GameActiviry.class);

            }


    }


    public void updateItem(){
       ItemModel item= ItemManager.getInstance().getItem();

        if(item!=null){
            gameListener.loadGameItem(item);
            gameListener.updateScoreView((int) ItemManager.getInstance().getCurrentScore());
        }

        else
            gameListener.onFailure("No item");
    }

    /**
     * This method will only be called when the user choose the right
     * @param timeInSeconds
     */
    public void saveQuizScore(String timeInSeconds){
        Log.d("QuizLog","time "+timeInSeconds);
        double timeTaken= TimeUtils.timerConverter(timeInSeconds);
        double score=GameScore.quizScoreCalculator(timeTaken);
        ItemManager.getInstance().setCurrentScore(score);
        ItemManager.getInstance().addAnsweredQuestions(true);
        int correct=ItemManager.getInstance().getNoOfQuestionsAnswered();
        int total=ItemManager.getInstance().getTotalNoOfQuestions();
        int percentage=GameScore.percentageCalculator(total,correct);
        ItemManager.getInstance().setPercentage(percentage);
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
            Log.d("DEBUG","answer does not exist to display");
        }

    }

    /**
     *
     * @param imageName name of the image
     */
    public void getImageUrl(final String imageName){
        Log.d("DEBUG","image urls of  "+imageName);

        ImageService.getUrl(imageName).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){

                    gameListener.setImage(task.getResult().toString());
                }
            }
        });

    }



    public interface GameListener extends ViewHandler{
        public void loadGameItem(ItemModel item);
        public void setImage(String url);
        public void loadQuizItem(Question quiz);
        public void updateScoreView(int score);
        public void highLightCorrect();
        public void highLightWrong(String answer);
    }




}
