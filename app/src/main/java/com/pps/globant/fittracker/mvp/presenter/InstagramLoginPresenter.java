package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.pps.globant.fittracker.mvp.view.InstagramLoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MyWVClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class InstagramLoginPresenter {
    private final static String CODE = "code";
    private static final String EQUAL_SIGN = "=";
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
        bus.post(new InformationReady(event.name, event.id));
    }

    public static class InformationReady {
        public final String name;
        public String id;

        public InformationReady(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }
}
