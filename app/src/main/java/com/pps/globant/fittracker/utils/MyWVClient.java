package com.pps.globant.fittracker.utils;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.otto.Bus;

import static com.pps.globant.fittracker.utils.CONSTANTS.REDIRECT_URI;

public class MyWVClient extends WebViewClient {

    Dialog dialog;
    Bus bus;

    public MyWVClient(Dialog dialog, Bus bus) {
        this.dialog = dialog;
        this.bus = bus;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.getUrl().toString().startsWith(REDIRECT_URI)) {
            bus.post(new PostForHandleUrl(request.getUrl().toString()));
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(REDIRECT_URI)) {
            bus.post(new PostForHandleUrl(url));
            return true;
        }
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        dialog.show();
    }

    public static class PostForHandleUrl{
        public final String url;

        public PostForHandleUrl(String url){
            this.url = url;
        }
    }
}
