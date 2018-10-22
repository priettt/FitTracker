package com.pps.globant.fittracker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamToString {

    private static final String EMPTY_STRING = "" ;

    public static String streamToString(InputStream is) throws IOException {
        String str = EMPTY_STRING;

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

            } finally {
                is.close();
            }
            str = sb.toString();
        }
        return str;
    }
}
