package com.pps.globant.fittracker.mvp.model;

import android.content.SharedPreferences;

import com.facebook.share.Share;
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

    public final static String AUTH_URL_FULL = String.format("%sclient_id=%s&redirect_uri=%s&response_type=code&display=touch", AUTHURL, CLIENT_ID, REDIRECT_URI);
    public final static String TOKEN_URL_FULL = String.format("%s?client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code", TOKENURL, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
    private final static String NOT_LOGED = "None user account logged in";
    public Bus bus;
    public SharedPreferences spUser;

    public InstagramLoginModel(Bus bus) {
        this.bus = bus;
        BusProvider.register(this);
    }

    public void getIGInformation(String code, MainActivity activity) {
        new MyAsyncTask(code,bus,spUser).execute();
    }

    public void isLoggedIn(SharedPreferences spUser) {
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
