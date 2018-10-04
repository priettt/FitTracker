package com.pps.globant.fittracker.mvp.model;

import android.content.SharedPreferences;

import com.pps.globant.fittracker.MainActivity;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MyAsyncTask;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import static android.content.Context.MODE_PRIVATE;
import static com.pps.globant.fittracker.utils.CONSTANTS.AUTHURL;
import static com.pps.globant.fittracker.utils.CONSTANTS.CLIENT_ID;
import static com.pps.globant.fittracker.utils.CONSTANTS.CLIENT_SECRET;
import static com.pps.globant.fittracker.utils.CONSTANTS.REDIRECT_URI;
import static com.pps.globant.fittracker.utils.CONSTANTS.SP;
import static com.pps.globant.fittracker.utils.CONSTANTS.SP_NAME;
import static com.pps.globant.fittracker.utils.CONSTANTS.SP_TOKEN;
import static com.pps.globant.fittracker.utils.CONSTANTS.TOKENURL;

public class InstagramLoginModel {

    private final static String AUTH_URL_FULL = AUTHURL + "client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&display=touch";
    private static String TOKEN_URL_FULL = TOKENURL + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";
    private final static String NOT_LOGED = "None user account logged in";
    public Bus bus;
    public SharedPreferences spUser;

    public InstagramLoginModel(Bus bus) {
        this.bus = bus;
        BusProvider.register(this);
    }

    public String getAuthUrlFull() {
        return AUTH_URL_FULL;
    }

    public static String getTokenUrlFull() {
        return TOKEN_URL_FULL;
    }

    public void getIGInformation(String code, MainActivity activity) {
        new MyAsyncTask(code,bus,spUser).execute();
    }

    public void isLoggedIn(MainActivity activity) {
        spUser= activity.getSharedPreferences(SP,MODE_PRIVATE);
        String token = spUser.getString(SP_TOKEN, null);
        String name = spUser.getString(SP_NAME, null);
        if (token != null) {
            bus.post(new RetIgInformation(name,true));
        }else {
            bus.post(new RetIgInformation(NOT_LOGED,false));
        }
    }

    public static class RetIgInformation {
        public String name;
        public boolean logeado;

        public RetIgInformation(String name) {
            this.name = name;
        }
        public RetIgInformation(String name, boolean logeado) {
            this.name = name;
            this.logeado = logeado;
        }
    }

    @Subscribe
    public void onPostReadyInformation(MyAsyncTask.PostReadyInformation event){
    bus.post(new RetIgInformation(event.name,true));
    }

}
