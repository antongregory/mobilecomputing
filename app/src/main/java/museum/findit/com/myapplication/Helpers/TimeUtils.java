package museum.findit.com.myapplication.Helpers;

/**
 * Created by antongregory on 25/10/2016.
 */

public class TimeUtils {

    public static double timerConverter(String minutesAndSeconds){
        String[] parts = minutesAndSeconds.split(":");
        String minuteString = parts[0];
        String secondString = parts[1];
        double minutesInSeconds=Double.parseDouble(minuteString)*60;
        double seconds=Double.parseDouble(secondString);

        return minutesInSeconds+seconds;
    }
}
