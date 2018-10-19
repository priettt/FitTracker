package com.pps.globant.fittracker.mvp.view;

import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public class ActivityView<T> {
    private final WeakReference<T> activityRef;

    ActivityView(T activity) {
        activityRef = new WeakReference<>(activity);
    }

    @Nullable
    public T getActivity() {
        return activityRef.get();
    }

    @Nullable
    public Context getContext() {
        return (Context) getActivity();
    }

}