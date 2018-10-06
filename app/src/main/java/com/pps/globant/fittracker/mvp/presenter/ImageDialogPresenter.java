package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.mvp.model.ImageDialogModel;
import com.pps.globant.fittracker.mvp.view.ImageDialogView;
import com.squareup.otto.Subscribe;

public class ImageDialogPresenter {
    private ImageDialogView view;
    private ImageDialogModel model;
    private ImageDialog.OnAcceptClickListener onAcceptClickListener;

    public ImageDialogPresenter(ImageDialogView view, ImageDialogModel model, ImageDialog.OnAcceptClickListener onAcceptClickListener) {
        this.view = view;
        this.model = model;
        this.onAcceptClickListener = onAcceptClickListener;
        init();
    }

    private void init() {
        view.display(model.getUrlFullResolution());
    }

    @Subscribe
    public void onAcceptClickPressedEvent(ImageDialogView.AcceptClickPressedEvent event) {
        onAcceptClickListener.onAcceptAvatar(model.getThumbnail());
        view.dismiss();
    }

    @Subscribe
    public void onCancelClickPressedEvent(ImageDialogView.onCancelClickPressedEvent event) {
        view.dismiss();
    }

}
