package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.app.Dialog;
import android.webkit.WebView;

import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.pps.globant.fittracker.utils.MyWVClient;
import com.squareup.otto.Bus;

public class InstagramLoginView extends ActivityView<Activity> {
    private Dialog dialog;

    private final Bus bus;

    public InstagramLoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        bus.register(this);
    }

    public void showDialog() {
        Activity activity = this.getActivity();
        if (activity == null) {
            return;
        }
        dialog = new Dialog(activity);
        WebView webView = new WebView(activity);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new MyWVClient(bus));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(InstagramLoginModel.AUTH_URL_FULL);
        dialog.setContentView(webView);
        dialog.show();
    }

    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
