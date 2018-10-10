package com.pps.globant.fittracker.mvp.presenter;

import android.app.Dialog;
import android.webkit.WebView;

import com.pps.globant.fittracker.MainActivity;
import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.pps.globant.fittracker.mvp.view.IntagramLoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.MyWVClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class InstagramLoginPresenter {
    private final static String CODE = "code";
    private final static String ERROR = "error";
    private static final String EQUAL_SIGN = "=";
    private final InstagramLoginModel model;
    private final IntagramLoginView view;
    public Dialog dialog;
    public MainActivity activity;
    public Bus bus;


    public InstagramLoginPresenter(Bus bus) {
        this.bus = bus;
        BusProvider.register(this);
        model = new InstagramLoginModel(bus);
        view = new IntagramLoginView();
    }

    public void igButtonLoginClick(MainActivity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        WebView webView = new WebView(activity);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new MyWVClient(dialog, bus));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(model.getAuthUrlFull());
        dialog.setContentView(webView);
    }


    public void handleUrl(String url) {
        String code;
        String temp[] = url.split(EQUAL_SIGN);
        if (url.contains(CODE)) {
            code = temp[1];
            model.getIGInformation(code, activity);
        }
    }

    @Subscribe
    public void onPostForHandleUrl(MyWVClient.PostForHandleUrl event) {
        String url = event.url;
        handleUrl(url);
    }

    @Subscribe
    public void onRetIgInformation(InstagramLoginModel.RetIgInformation event) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        bus.post(new InformationReady(event.name, event.logeado));
    }

    public void isLoggedIn(MainActivity activity) {
        model.isLoggedIn(activity);
    }

    public static class InformationReady {
        public final String name;
        public boolean logeado;

        public InformationReady(String name, boolean logeado) {
            this.name = name;
            this.logeado = logeado;
        }

        public InformationReady(String name) {
            this.name = name;
        }
    }
}
