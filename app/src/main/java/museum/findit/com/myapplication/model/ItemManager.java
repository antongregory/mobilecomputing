package museum.findit.com.myapplication.model;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antongregory on 13/10/2016.
 */

public class ItemManager implements Manager,QuestionHandler{


    ItemModel item;
    Question question;
    ArrayList<Question> questionList;
    ArrayList<ItemModel> itemCollection;

    int currentItemIndex,currentQuestionIndex;

    SharedPreferences itemListData;
    private static ItemManager itemManager;

    //// TODO: 19/10/2016   ADD shared preference

        ItemManager(){
            loadDummyData();
            currentItemIndex=0;
            currentQuestionIndex=0;
        }


    public void setUserName(String username){

        Log.d("DEBUG","username is "+username);
    }

    public static ItemManager getInstance() {
        if (itemManager == null) {
            itemManager = new ItemManager();
        }
        return itemManager;
    }



    public void loadList(ArrayList itemCollection){

        itemCollection=itemCollection;


    }


    public void loadDummyData(){
        ItemModel item=new ItemModel();
        Question question1=new Question();

        ArrayList<Question> questionList1=new ArrayList<Question>();
        itemCollection=new ArrayList<>();
        question1.setQuestion("Which culture does this item belong");
        question1.setChoiceA("China");
        question1.setChoiceB("Turkey");
        question1.setChoiceC("Indonesia");
        question1.setAnswer("Indonesia");

        questionList1.add(question1);

        item.setBarcodeId("indonesia");
        item.setDescription("Cultural bg");
        item.setImage_url("hello");
        item.setQuestions(questionList1);
        itemCollection.add(item);
        //second item
        ItemModel item2=new ItemModel();
        Question question2=new Question();

        ArrayList<Question> questionList2=new ArrayList<Question>();
        question2.setQuestion("Who rode this boat");
        question2.setChoiceA("Trump");
        question2.setChoiceB("Clinton");
        question2.setChoiceC("Zlatan");
        question2.setAnswer("Zlatan");

        questionList2.add(question2);

        item2.setBarcodeId("zlatan");
        item2.setDescription("Cultural bg");
        item2.setImage_url("hello");
        item2.setQuestions(questionList2);
        itemCollection.add(item2);
    }

    public int count(){

        return itemCollection.size();
    }
    @Override
    public boolean checkNextItem() {
        if (currentItemIndex<count())
            return true;
        else
        return false;
    }

    public boolean compareBarCode(String barcode){
       String barcodeFromItem = "";

        if (checkNextItem()){
            barcodeFromItem=itemCollection.get(currentItemIndex).getBarcodeId();

            if(barcodeFromItem.equals(barcode)){
                return true;
            }
            else 
                return false;
        
        }else{

            return false;
        }
    }

    private void syncItemNumber(){
        //stores the item index
    }




    @Override
    public ItemModel getItem() {

        if(currentItemIndex<count()){
            item= itemCollection.get(currentItemIndex);
            return item;
        }
        else{
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
        Log.d("DEBUG","currentQuestionIndex code "+currentQuestionIndex);
        Log.d("DEBUG","current item code "+currentItemIndex);
        Log.d("DEBUG","count item code "+count());
        if(currentQuestionIndex<itemCollection.get(currentItemIndex).getQuestions().size())
        return true;
        else
            return false;
    }

    @Override
    public Question loadNextQuestion() {
        //retuns the next question from the list
        Question question;
        if(checkQuestionExist()){
            question= itemCollection.get(currentItemIndex).getQuestions().get(currentQuestionIndex);
            return question;
        }
        else
        {
            currentQuestionIndex=0;
            currentItemIndex++;
            return null;
        }


    }

    private ItemModel getCurrentItem(){
        if (currentItemIndex<count()){

        return itemCollection.get(currentItemIndex);
        }
        else
            return null;
    }

    public String getCurrentQuestionAnswer(){
        String answer;
        Log.d("DEBUG","question is "+checkQuestionExist());
        if (checkQuestionExist()){
            answer= item.getQuestions().get(currentQuestionIndex).getAnswer();
            currentQuestionIndex++;
            return answer;
        }
        else{
            return null;
        }
    }


    @Override
    public boolean checkAnswer(String answer) {
        ItemModel item=getCurrentItem();
        boolean result;
        if (checkQuestionExist())
        result=item.getQuestions().get(currentQuestionIndex).getAnswer().equals(answer);
        else
        return false;
        return result;
    }
}
