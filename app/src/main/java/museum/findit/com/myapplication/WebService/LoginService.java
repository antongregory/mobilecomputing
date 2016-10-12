package museum.findit.com.myapplication.WebService;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    public Task<AuthResult> login(){
        return FirebaseAuth.getInstance().signInAnonymously();
    }

    public String getId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
