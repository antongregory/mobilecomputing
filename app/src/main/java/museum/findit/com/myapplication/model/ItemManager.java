package museum.findit.com.myapplication.model;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import museum.findit.com.myapplication.WebService.GameService;

/**
 * Created by antongregory on 13/10/2016.
 */

public class ItemManager implements Manager, QuestionHandler {

    private String username;
    ItemModel item;
    int  percentage;
    Question question;
    ArrayList<Question> questionList;
    ArrayList<ItemModel> itemCollection;
    ArrayList<ItemModel> completeCollection;
    int noOfQuestionsAnswered,totalNoOfQuestions;

    int currentItemIndex, currentQuestionIndex;
    double currentScore;

    SharedPreferences itemListData;
    private static ItemManager itemManager;


    ItemManager() {

        currentItemIndex = 0;
        currentQuestionIndex = 0;

    }


    public void setUserName(String username) {
        this.username = username;
        Log.d("DEBUG", "username is " + username);
    }

    public String getUserName() {
        return username;
    }

    public static ItemManager getInstance() {
        if (itemManager == null) {
            itemManager = new ItemManager();
        }
        return itemManager;
    }
    public void setPercentage(int percentageValue){
               percentage=percentageValue;
    }

    public int getPercentage(){

        return percentage;
    }
    public void setCurrentScore(double score){
        currentScore+=score;
    }

    public double getCurrentScore(){
        return currentScore;
    }
    public int getCurrentItemIndex() {

        return currentItemIndex;
    }

    public int getCurrentQuestionIndex() {

        return currentQuestionIndex;
    }

        //// TODO: 25/10/2016  clean code 
    //// TODO: 25/10/2016 add persistence to the fields required 
    public void loadList(ArrayList<ItemModel> itemcollection, Integer seed) {
        completeCollection = itemcollection;
        if (seed != null)
            Collections.shuffle(completeCollection, new Random(seed));
        else
            Collections.shuffle(completeCollection, new Random(5));
        Log.d("DEBUG", "game seed" + seed);
        itemCollection = new ArrayList<ItemModel>(completeCollection.subList(0, 5));
        
        setPercentage(0);
        setCurrentScore(0);
        noOfQuestionsAnswered=0;
        totalNoOfQuestions=0;


    }


    public int count() {

        return itemCollection.size();
    }

    @Override
    public boolean checkNextItem() {
        if (currentItemIndex < count())
            return true;
        else
            return false;
    }

    public boolean compareBarCode(String barcode) {
        String barcodeFromItem = "";

        if (checkNextItem()) {
            barcodeFromItem = itemCollection.get(currentItemIndex).getBarcodeId();

            if (barcodeFromItem.equals(barcode)) {
                return true;
            } else
                return false;

        } else {

            return false;
        }
    }

    private void syncItemNumber() {
        //stores the item index
    }


    @Override
    public ItemModel getItem() {

        if (currentItemIndex < count()) {
            item = itemCollection.get(currentItemIndex);

            item.setOrderAndCount(currentItemIndex + 1 + "/" + count());
            return item;
        } else {
            currentItemIndex = 0;
            return null;
        }


    }

    @Override
    public void saveItems() {
        // save the items in the sharedpreference


    }

    @Override
    public void saveScore(int score) {

        //save score to sharedpreference
    }

    @Override
    public boolean checkQuestionExist() {
        Log.d("DEBUG", "currentQuestionIndex code " + currentQuestionIndex);
        Log.d("DEBUG", "current item code " + currentItemIndex);
        Log.d("DEBUG", "count item code " + count());
        if (currentQuestionIndex < itemCollection.get(currentItemIndex).getQuestions().size())
            return true;
        else
            return false;
    }

    @Override
    public Question loadNextQuestion() {

        Question question;
        if (checkQuestionExist()) {

            question = itemCollection.get(currentItemIndex).getQuestions().get(currentQuestionIndex);
            int count =getCurrentItem().getQuestions().size();
            question.setOrderAndCount(currentQuestionIndex + 1 + "/" + count);
            totalNoOfQuestions++;
            return question;
        } else {
            currentQuestionIndex = 0;
            currentItemIndex++;
            return null;
        }
    }

    public void addAnsweredQuestions(boolean hasAnswered){
        if (hasAnswered==true)
        {   noOfQuestionsAnswered++;

        }
    }

    public int getNoOfQuestionsAnswered(){

        return noOfQuestionsAnswered;
    }

    public int getTotalNoOfQuestions(){

        return totalNoOfQuestions;
    }




    private ItemModel getCurrentItem() {
        if (currentItemIndex < count()) {

            return itemCollection.get(currentItemIndex);
        } else
            return null;
    }

    public String getCurrentQuestionAnswer() {
        String answer;
        Log.d("DEBUG", "question is " + checkQuestionExist());
        if (checkQuestionExist()) {
            answer = item.getQuestions().get(currentQuestionIndex).getAnswer();
            currentQuestionIndex++;
            return answer;
        } else {
            return null;
        }
    }


    @Override
    public boolean checkAnswer(String answer) {
        ItemModel item = getCurrentItem();
        boolean result;
        if (checkQuestionExist())
            result = item.getQuestions().get(currentQuestionIndex).getAnswer().equals(answer);
        else
            return false;
        return result;
    }
}
