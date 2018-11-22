package com.pps.globant.fittracker.mvp.view;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.utils.ImageLoadedCallback;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageDialogView extends DialogView {

    @BindView(R.id.dialog_image) ImageView image;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.dialog_avatar_buton_cancel)
    Button btn_cancel;
    private Bus bus;

    public ImageDialogView(Dialog dialog, Bus bus) {
        super(dialog);
        ButterKnife.bind(this, dialog);
        this.bus = bus;
    }

    @OnClick(R.id.dialog_avatar_buton_cancel)
    public void onCancelClick() {
        bus.post(new onCancelClickPressedEvent());
    }

    public void dismiss() {
        if (getDialog() == null) return;
        getDialog().dismiss();
    }

    @OnClick(R.id.dialog_avatar_buton_accept)
    public void onAcceptClick() {
        btn_cancel.setVisibility(View.VISIBLE);
        bus.post(new AcceptClickPressedEvent());
    }

    public void display(String imageUrl) {
        Picasso.get().
                load(imageUrl)
                .into(image, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void disableCancel() {
        btn_cancel.setVisibility(View.GONE);
    }

    public class AcceptClickPressedEvent {
    }

    public class onCancelClickPressedEvent {
    }
}