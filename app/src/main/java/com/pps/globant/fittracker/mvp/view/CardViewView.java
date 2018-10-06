package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import java.lang.ref.WeakReference;

public class CardViewView {
    private final WeakReference<CardView> cardRef;
    private final WeakReference<Activity> activityRef;

    public CardViewView(CardView card, Activity activity) {
        cardRef = new WeakReference<>(card);
        activityRef =  new WeakReference<>(activity);
    }

    @Nullable
    public CardView getCardView() {
        return cardRef.get();
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
