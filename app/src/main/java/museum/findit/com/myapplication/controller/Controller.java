package museum.findit.com.myapplication.controller;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

import museum.findit.com.myapplication.WebService.GameOwnerService;
import museum.findit.com.myapplication.WebService.GameParticipantService;
import museum.findit.com.myapplication.WebService.ItemService;
import museum.findit.com.myapplication.WebService.LoginService;
import museum.findit.com.myapplication.model.CurrentUser;
import museum.findit.com.myapplication.model.ItemManager;
import museum.findit.com.myapplication.model.ItemModel;
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
    public void loginAction(final String username){
        if(username.trim().length()==0){
            viewListener.onFailure("User name cannot be empty");
        }
        else{
            LoginService.login().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        ItemManager.getInstance().setUserName(username);
                        viewListener.onSucess(JoinGameActivity.class);
                    } else {
                        viewListener.onFailure("Login failed. Try again later");
                    }
                }
            });
        }


    }



    public void joinGameAction(String gameCode){

        //Call the corresponding service
        //on sucess load the view
        String username = ItemManager.getInstance().getUserName();
        GameParticipantService.join(gameCode, username).addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if(task.isSuccessful() && task.getResult()){
                    CurrentUser.setAsParticipant();
                    viewListener.onSucess(WaitingRoomActivity.class);
                } else {
                    viewListener.onFailure("Joining game failed");
                }
            }
        });
    }

    /**
     *
     * @return game code
     */
    public String createGameAction(){

        //Call the corresponding service
        //on sucess load the view
        String username = ItemManager.getInstance().getUserName();
        String gameCode = GameOwnerService.create(username);
        CurrentUser.setAsOwner();

        return gameCode;
    }


    public void startLoadingData(){
            ItemManager.getInstance().loadDummyData();
        ItemService.getAll().addOnCompleteListener(new OnCompleteListener<ArrayList<ItemModel>>() {
            @Override
            public void onComplete(@NonNull Task<ArrayList<ItemModel>> task) {
                if(task.isSuccessful()){
                    // TODO: 2016-10-23 use items instead of dummy data 
                    ArrayList<ItemModel> items = task.getResult();
                }
            }
        });
        viewListener.onSucess(GameActiviry.class);
        //initiate service and pass data to model
    }

    public void compareBarCode(String barcode){

   /*if(ItemManager.getInstance().compareBarCode(barcode)){
            viewListener.onSucess(QuizActivity.class);
        }
        else{
            viewListener.onFailure();
        }*/
        viewListener.onSucess(QuizActivity.class);
    }

    public interface ViewHandler {

        public void onSucess(Class view);
        public void onFailure(String message);

    }





}
