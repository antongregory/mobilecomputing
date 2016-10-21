package museum.findit.com.myapplication.controller;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import museum.findit.com.myapplication.WebService.GameOwnerService;
import museum.findit.com.myapplication.WebService.LoginService;
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
        viewListener.onSucess(WaitingRoomActivity.class);
    }

    public void createGameAction(){

        //Call the corresponding service
        //on sucess load the view
        String username = ItemManager.getInstance().getUserName();
        GameOwnerService.create(username);
        viewListener.onSucess(WaitingRoomActivity.class);
    }


    public void startLoadingData(){
            ItemManager.getInstance().loadDummyData();
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
