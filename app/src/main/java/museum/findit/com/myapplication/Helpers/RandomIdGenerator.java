package museum.findit.com.myapplication.Helpers;

import java.util.Random;

/**
 * Created by doniramadhan on 2016-10-12.
 */

public class RandomIdGenerator {
    private static char[] _base36chars =
            "0123456789abcdefghijklmnopqrstuvwxyz"
                    .toCharArray();

    private static Random _random = new Random();

    public static String GetBase36(int length)
    {
        StringBuilder sb = new StringBuilder(length);

        for (int i=0; i<length; i++)
            sb.append(_base36chars[_random.nextInt(36)]);

        return sb.toString();
    }
}
