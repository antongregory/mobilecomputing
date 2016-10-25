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


    ItemModel item;

    ArrayList<ItemModel> itemCollection;
    ArrayList<ItemModel> completeCollection;

    public Player playerProfile;
    private static ItemManager itemManager;


    ItemManager() {


        playerProfile=new Player();

    }

    public Player getPlayerProfile(){
        return playerProfile;
    }



    public static ItemManager getInstance() {
        if (itemManager == null) {
            itemManager = new ItemManager();
        }
        return itemManager;
    }

    //// TODO: 25/10/2016 add persistence to the fields required 
    public void loadList(ArrayList<ItemModel> itemcollection, Integer seed) {
        completeCollection = itemcollection;
        if (seed != null)
            Collections.shuffle(completeCollection, new Random(seed));
        else
            Collections.shuffle(completeCollection, new Random(5));
        Log.d("DEBUG", "game seed" + seed);
        itemCollection = new ArrayList<ItemModel>(completeCollection.subList(0, 5));
        
        playerProfile.clear();


    }


    public int count() {

        return itemCollection.size();
    }

    @Override
    public boolean checkNextItem() {
        if (playerProfile.getCurrentItemIndex() < count())
            return true;
        else
            return false;
    }

    /**
     *
     * @param barcode of the item to be compared
     * @return true if barcode is right and false if barcode is  wrong
     */
    @Override
    public boolean compareBarCode(String barcode) {
        String barcodeFromItem = "";

        if (checkNextItem()) {
            barcodeFromItem = itemCollection.get(playerProfile.getCurrentItemIndex()).getBarcodeId();

            if (barcodeFromItem.equals(barcode)) {
                return true;
            } else
                return false;

        } else {

            return false;
        }
    }


    @Override
    public ItemModel getItem() {

        if (playerProfile.getCurrentItemIndex() < count()) {
            item = itemCollection.get(playerProfile.getCurrentItemIndex());

            item.setOrderAndCount(playerProfile.getCurrentItemIndex() + 1 + "/" + count());
            return item;
        } else {
            playerProfile.setCurrentItemIndex(0);
            return null;
        }


    }


    @Override
    public boolean checkQuestionExist() {
 
        Log.d("DEBUG", "current item code " + playerProfile.getCurrentItemIndex());

        if (playerProfile.getCurrentQuestionIndex() < getCurrentItem().getQuestions().size())
            return true;
        else
            return false;
    }

    @Override
    public Question loadNextQuestion() {

        Question question;
        int currentItemIndex=playerProfile.getCurrentItemIndex();
        int currentQuestionIndex=playerProfile.getCurrentQuestionIndex();
        int totalNoOfQuestions=playerProfile.getTotalNoOfQuestions();
        if (checkQuestionExist()) {

            question = getCurrentItem().getQuestions().get(currentQuestionIndex);
            int count =getCurrentItem().getQuestions().size();
            question.setOrderAndCount(currentQuestionIndex + 1 + "/" + count);
            totalNoOfQuestions++;
            playerProfile.setTotalNoOfQuestions(totalNoOfQuestions);
            return question;
        } else {

            playerProfile.setCurrentQuestionIndex(0);
            currentItemIndex++;
            playerProfile.setCurrentItemIndex(currentItemIndex);
            return null;
        }
    }

    public void addAnsweredQuestions(boolean hasAnswered){
        int noOfQuestionsAnswered=playerProfile.getNoOfQuestionsAnswered();;
        if (hasAnswered==true)
        {

            noOfQuestionsAnswered++;
            playerProfile.setNoOfQuestionsAnswered(noOfQuestionsAnswered);
        }
    }

    /**
     *
     * @return returns the current item if item exist and returns null if there is no item
     *
     *
     */
    private ItemModel getCurrentItem() {
        if (playerProfile.getCurrentItemIndex() < count()) {

            return itemCollection.get(playerProfile.getCurrentItemIndex());
        } else
            return null;
    }

    /**
     *
     * @return returns the answer of current question and returns null if there is no item
     *
     *
     */
    public String getCurrentQuestionAnswer() {
        String answer;
        int currentQuestionIndex=playerProfile.getCurrentQuestionIndex();
        Log.d("DEBUG", "question is " + checkQuestionExist());
        if (checkQuestionExist()) {

            answer = getCurrentItem().getQuestions().get(currentQuestionIndex).getAnswer();
            currentQuestionIndex++;
            playerProfile.setCurrentQuestionIndex(currentQuestionIndex);
            return answer;
        } else {
            return null;
        }
    }


    public void setUserName(String username) {
        this.playerProfile.setUsername(username);
        Log.d("DEBUG", "username is " + username);
    }

    public String getUserName() {
        return playerProfile.getUsername();
    }

}
