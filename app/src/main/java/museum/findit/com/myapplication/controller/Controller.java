package museum.findit.com.myapplication.controller;

import android.util.Log;

import museum.findit.com.myapplication.model.ItemManager;
import museum.findit.com.myapplication.view.Activities.GameActiviry;
import museum.findit.com.myapplication.view.Activities.JoinGameActivity;
import museum.findit.com.myapplication.view.Activities.QuizActivity;
import museum.findit.com.myapplication.view.Activities.WaitingRoomActivity;

/**
 * Created by antongregory on 05/10/2016.
 */

public class Controller {


    private ViewHandler viewListener;
    public Controller(ViewHandler viewListener) {

        this.viewListener=viewListener;
       //use model manager


    }

    //login check
    //start leave
    //scan bar code & check
    //load quiz question & load quiz activity
    //load item and item activity

    //login check
    public void loginAction(String username){
        if(username.trim().length()==0){
            viewListener.displayFailMessage();
        }
        else{
            ItemManager.getInstance().setUserName(username);
            viewListener.loadNextView(JoinGameActivity.class);
        }


    }



    public void joinGameAction(String gameCode){

        //Call the corresponding service
        //on sucess load the view
        viewListener.loadNextView(WaitingRoomActivity.class);
    }

    public void createGameAction(){

        //Call the corresponding service
        //on sucess load the view
        viewListener.loadNextView(WaitingRoomActivity.class);
    }


    public void startLoadingData(){
            ItemManager.getInstance().loadDummyData();
        viewListener.loadNextView(GameActiviry.class);
        //initiate service and pass data to model
    }

    public void compareBarCode(String barcode){

   if(ItemManager.getInstance().compareBarCode(barcode)){
            viewListener.loadNextView(QuizActivity.class);
        }
        else{
            viewListener.displayFailMessage();
        }
        //viewListener.loadNextView(QuizActivity.class);
    }

    public interface ViewHandler {

        public void loadNextView(Class view);
        public void displayFailMessage();

    }





}
