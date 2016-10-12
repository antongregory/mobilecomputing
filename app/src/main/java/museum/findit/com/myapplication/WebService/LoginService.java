package museum.findit.com.myapplication.WebService;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by doniramadhan on 2016-10-11.
 */

public class LoginService {
    private static LoginService singleton = null;
    private LoginService() {  }
    public static LoginService shared() {
        if (singleton == null) {
            singleton = new LoginService();
        }
        return singleton;
    }

    public void login(){
        FirebaseAuth.getInstance().signInAnonymously();
    }
}
