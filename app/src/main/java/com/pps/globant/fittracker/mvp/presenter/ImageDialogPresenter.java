package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.mvp.model.ImageDialogModel;
import com.pps.globant.fittracker.mvp.view.ImageDialogView;
import com.squareup.otto.Subscribe;

public class ImageDialogPresenter {
    private ImageDialogView view;
    private ImageDialogModel model;
    private ImageDialog.OnAcceptClickListener onAcceptClickListener;
    private ImageDialog.OnAcceptClickListenerForExercise onAcceptClickListenerForExercise;

    public ImageDialogPresenter(ImageDialogView view, ImageDialogModel model, ImageDialog.OnAcceptClickListener onAcceptClickListener) {
        this.view = view;
        this.model = model;
        this.onAcceptClickListener = onAcceptClickListener;
        init();
    }
    public ImageDialogPresenter(ImageDialogView view, ImageDialogModel model, ImageDialog.OnAcceptClickListenerForExercise onAcceptClickListenerForExercise) {
        this.view = view;
        this.model = model;
        this.onAcceptClickListenerForExercise = onAcceptClickListenerForExercise;
        init();
    }

    private void init() {
        view.display(model.getUrlFullResolution());
    }

    @Subscribe
    public void onAcceptClickPressedEvent(ImageDialogView.AcceptClickPressedEvent event) {
        if (onAcceptClickListener != null){
            onAcceptClickListener.onAcceptAvatar(model.getThumbnail());
        }
        view.dismiss();
    }

    @Subscribe
    public void onCancelClickPressedEvent(ImageDialogView.onCancelClickPressedEvent event) {
        view.dismiss();
    }

}
