package com.pps.globant.fittracker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static final int INT_DOS = 2;
    private static final String MD5 = "MD5";
    private static final int ZERO = 0xFF;
    private static final String STRING_ZERO = "0";
    private static final String EMPTY_STRING = "";

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(ZERO & aMessageDigest);
                while (h.length() < INT_DOS)
                    h = STRING_ZERO + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return EMPTY_STRING;
    }
}
