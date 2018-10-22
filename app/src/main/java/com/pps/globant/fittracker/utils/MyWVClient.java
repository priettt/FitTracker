package com.pps.globant.fittracker.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.otto.Bus;

import static com.pps.globant.fittracker.utils.Constants.REDIRECT_URI;

public class MyWVClient extends WebViewClient {
    Bus bus;

    public MyWVClient(Bus bus) {
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

    public static class PostForHandleUrl{
        public final String url;
        public PostForHandleUrl(String url){
            this.url = url;
        }
    }
}
