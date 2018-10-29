package com.pps.globant.fittracker.mvp.presenter;

import android.content.SharedPreferences;

import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.pps.globant.fittracker.mvp.view.InstagramLoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MyWVClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import static com.pps.globant.fittracker.utils.CONSTANTS.SP_NAME;
import static com.pps.globant.fittracker.utils.CONSTANTS.SP_TOKEN;
import static com.pps.globant.fittracker.utils.CONSTANTS.USER_ID;

public class InstagramLoginPresenter {
    private final static String CODE = "code";
    private static final String EQUAL_SIGN = "=";
    private final static String NOT_LOGGED = "None user account logged in";
    private final static String EMPTY_STRING = "";
    private final static String SPACE_STRING = "";
    private final static String IG_PREFIX = "IG";
    private final InstagramLoginModel model;
    private final InstagramLoginView view;
    public Bus bus;


    public InstagramLoginPresenter(Bus bus, InstagramLoginModel model, InstagramLoginView view) {
        this.bus = bus;
        BusProvider.register(this);
        this.model = model;
        this.view = view;
    }

    public void igButtonLoginClick() {
        view.showDialog();
    }

    @Subscribe
    public void onPostForHandleUrl(MyWVClient.PostForHandleUrl event) {
        String url = event.url;
        String code;
        String temp[] = url.split(EQUAL_SIGN);
        if (url.contains(CODE)) {
            code = temp[1];
            model.getIGInformation(code);
        }
    }

    @Subscribe
    public void onRetIgInformation(InstagramLoginModel.RetIgInformation event) {
        view.closeDialog();
        String names[] = event.name.split(SPACE_STRING,2);
        String id = String.format("%s%s", IG_PREFIX, event.id);
        bus.post(new InformationReady(event.name, event.logeado, event.id));
        bus.post(new IgUserDataRecoveredEvent(User.getUser(names[0], names[1], null, null, id)));
    }

    public void isLoggedIn(SharedPreferences spUser) {
        String token = spUser.getString(SP_TOKEN, null);
        String name = spUser.getString(SP_NAME, null);
        String id = spUser.getString(USER_ID, null);
        if (token != null) {
            bus.post(new InformationReady(name, true, id));
        } else {
            bus.post(new InformationReady(NOT_LOGGED, false, id));
        }
    }

    public static class InformationReady {
        public final String name;
        public boolean logeado;
        public String id;

        public InformationReady(String name, boolean logeado, String id) {
            this.name = name;
            this.logeado = logeado;
            this.id = id;
        }
    }

    public class IgUserDataRecoveredEvent {
        public final User user;

        public IgUserDataRecoveredEvent(User user) {
            this.user = user;
        }
    }
}
