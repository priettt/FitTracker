package com.pps.globant.fittracker.utils;

public class Constants {
    public static final String CLIENT_ID = "f25ac60a2f434a6aafe6140d9cb51de3";
    public static final String CLIENT_SECRET = "b5c8d056911442f68ee482bf976ba449";
    public static final String REDIRECT_URI = "http://sociallogin.insta";

    public static final String AUTHURL = "https://instagram.com/oauth/authorize/?";
    public static final String TOKENURL = "https://api.instagram.com/oauth/access_token";

    public static final String SP = "SP_USER";
    public static final String SP_TOKEN = "TOKEN";
    public static final String SP_NAME = "NAME";
    public static final String SP_CODE = "CODE";
    public static final String USER_ID = "ID";

    public static final String EXTRA_MESSAGE = "com.pps.globant.fittracker.USERID";
    public static final String PATH_VECTOR = "com.pps.globant.fittracker.PATH_VECTOR";


    public static final int RC_GET_TOKEN = 9002;

    /*GOOGLE_SERVICE_CLIENT_ID is a key obtained from https://developers.google.com/identity/sign-in/android/start-integrating
    To get it, it requires an unique SHA1 key, so if you want to recompile the app in another pc, you'll need to create a new key.*/

    // Francisco's key
    // public static final String GOOGLE_SERVICE_CLIENT_ID = "268582315609-j419amnke1b8djg935oq1ncd08e78lam.apps.googleusercontent.com";
    // Gregorio's key
    // public static final String GOOGLE_SERVICE_CLIENT_ID = "602409589834-6b03kf79u58uel71s3k840mjie1mrhm9.apps.googleusercontent.com";
    // Fermin's key
    // public static final String GOOGLE_SERVICE_CLIENT_ID = "146472501375-hu9vlk5qk9svo2m2b2gbt0kb0fnvfrvd.apps.googleusercontent.com";
    public static final String GOOGLE_SERVICE_CLIENT_ID = "268582315609-j419amnke1b8djg935oq1ncd08e78lam.apps.googleusercontent.com";
    public static final int POLYLINE_WIDTH = 5 ;
}
