package com.pps.globant.fittracker.mvp.model;

import android.content.SharedPreferences;

import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MyAsyncTask;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import static com.pps.globant.fittracker.utils.CONSTANTS.AUTHURL;
import static com.pps.globant.fittracker.utils.CONSTANTS.CLIENT_ID;
import static com.pps.globant.fittracker.utils.CONSTANTS.CLIENT_SECRET;
import static com.pps.globant.fittracker.utils.CONSTANTS.REDIRECT_URI;
import static com.pps.globant.fittracker.utils.CONSTANTS.TOKENURL;

public class InstagramLoginModel {

    public final static String AUTH_URL_FULL = String.format("%sclient_id=%s&redirect_uri=%s&response_type=code&display=touch", AUTHURL, CLIENT_ID, REDIRECT_URI);
    public final static String TOKEN_URL_FULL = String.format("%s?client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code", TOKENURL, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
    private Bus bus;
    private SharedPreferences spUser;

    public InstagramLoginModel(Bus bus, SharedPreferences spUser) {
        this.bus = bus;
        BusProvider.register(this);
        this.spUser = spUser;
    }

    public void getIGInformation(String code) {
        new MyAsyncTask(code, bus, spUser).execute();
    }

    public static class RetIgInformation {
        public String name;
        public String id;
        public boolean logeado;

        public RetIgInformation(String name, boolean logeado, String id) {
            this.name = name;
            this.id = id;
            this.logeado = logeado;
        }
    }

    @Subscribe
    public void onPostReadyInformation(MyAsyncTask.PostReadyInformation event) {
        bus.post(new RetIgInformation(event.name, true, event.id));
    }

}
