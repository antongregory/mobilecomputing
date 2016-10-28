package museum.findit.com.myapplication.model;

import java.util.Objects;

/**
 * Created by doniramadhan on 2016-10-21.
 */

public class CurrentUser {
    private static String role;

    public static void setAsOwner(){
        role = "owner";
    }

    public static void setAsParticipant(){
        role = "participant";
    }

    public static Boolean isOwner(){
        return "owner".equals(role);
    }

    public static Boolean isParticipant(){
        return "participant".equals(role);
    }
}
