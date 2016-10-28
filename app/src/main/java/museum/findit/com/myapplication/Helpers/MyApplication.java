package museum.findit.com.myapplication.Helpers;

import android.app.Application;

/**
 * Created by Hui on 10/23/2016.
 */

public class MyApplication extends Application {

    private String userName;
    private String gameCode;
    private boolean owner;


    public String getGameCode() {
        return gameCode;
    }




    public void setGameCode(String someVariable) {
        this.gameCode = someVariable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String someVariable) {
        this.userName = someVariable;
    }


    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean someVariable) {
        this.owner = someVariable;
    }

}