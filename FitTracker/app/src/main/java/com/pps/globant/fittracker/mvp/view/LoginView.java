package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.widget.TextView;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.text_fb_label) TextView textFbLabel;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void setLabelFb(String label) {
        textFbLabel.setText(label);
    }

}
