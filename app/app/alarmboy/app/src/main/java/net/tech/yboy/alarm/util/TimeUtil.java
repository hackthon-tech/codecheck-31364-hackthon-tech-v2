package net.tech.yboy.alarm.util;

/**
 * Created by manabu on 2018/03/21.
 */

public class TimeUtil {

    public static int[] toInt(String time) {

        try {
            String[] s = time.split(":");
            String h = s[0];
            String m = s[1];
            return new int[]{
                    Integer.parseInt(h),
                    Integer.parseInt(m)
            };
        } catch (Throwable t) {
            return null;
        }
    }

    public static String toString(int hour, int minute) {
        String result = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        return result;
    }


}
