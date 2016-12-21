package com.mvp.mvp.helper;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by TEAM on 12/4/2016.
 * That's it
 */

public class Utils {

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }

        return hex.toString();
    }

    public static void LogNp(String log1, String log2) {
        if (log2.length() > 500) {
            try {
                int maxLogSize = 500;
                for (int i = 0; i <= log2.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > log2.length() ? log2.length() : end;
                    Log.e(log1, log2.substring(start, end));
                }
            } catch (Exception e) {
            }
        } else {
            Log.e(log1, log2);
        }
    }
}
