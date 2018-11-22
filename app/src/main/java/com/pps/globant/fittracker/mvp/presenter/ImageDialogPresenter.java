package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.model.UrlGetter;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.model.fitness.Exercise;
import com.pps.globant.fittracker.mvp.model.ImageDialogModel;
import com.pps.globant.fittracker.mvp.view.ImageDialogView;
import com.squareup.otto.Subscribe;

public class ImageDialogPresenter {
    private ImageDialogView view;
    private ImageDialogModel model;
    private ImageDialog.OnAcceptClickListener onAcceptClickListener;

    public ImageDialogPresenter(ImageDialogView view, ImageDialogModel model, ImageDialog.OnAcceptClickListener onAcceptClickListener, UrlGetter urlGetter) {
        this.view = view;
        this.model = model;
        if (onAcceptClickListener != null){
            this.onAcceptClickListener = onAcceptClickListener;
        }else {
            view.disableCancel();
        }
        model.setUrlGetter(urlGetter);
        init();
    }

    private void init() {
        view.display(model.getUrlFullResolution());
    }

    @Subscribe
    public void onAcceptClickPressedEvent(ImageDialogView.AcceptClickPressedEvent event) {
        if (onAcceptClickListener != null){
            onAcceptClickListener.onAcceptAvatar((Thumbnail)model.getUrlGetter());
        }
        view.dismiss();
    }

    @Subscribe
    public void onCancelClickPressedEvent(ImageDialogView.onCancelClickPressedEvent event) {
        view.dismiss();
    }

}
