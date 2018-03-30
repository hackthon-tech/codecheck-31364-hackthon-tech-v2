package net.tech.yboy.alarm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by manabu on 2018/03/21.
 */

public class UidUtil {
    public static String createUid() {
        MessageDigest md5 = null;
        byte[] hash = null;
        String value = System.currentTimeMillis() + "";
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes());
            hash = md5.digest();
            String s = "";
            int size = hash.length;
            for (int i = 0; i < size; i++) {
                int n = hash[i];
                if (n < 0) {
                    n += 256;
                }
                String hex = Integer.toHexString(n);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                s += hex;
            }
            return s;
        } catch (NoSuchAlgorithmException e) {
        }

        return null;
    }

}
