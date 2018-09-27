package com.pps.globant.fittracker.mvp.view;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public class ActivityView {
    private final WeakReference<Activity> activityRef;

    ActivityView(Activity activity) {
        activityRef = new WeakReference<>(activity);
    }

    @Nullable
    public Activity getActivity() {
        return activityRef.get();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

}