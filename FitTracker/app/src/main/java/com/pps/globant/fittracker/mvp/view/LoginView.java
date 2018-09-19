package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.widget.TextView;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.hello_label) TextView helloLabel;
    @BindView(R.id.fb_label) TextView fbLabel;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void setLabel(String label) {
        helloLabel.setText(label);
    }

    @OnClick(R.id.login_button)
    public void loginWithFbButtonPressed()
    {
        bus.post(new LoginWithFbButtonPressedEvent());
    }

    public void setLabelFb(String label) {
        fbLabel.setText(label);
    }

    public static class LoginWithFbButtonPressedEvent {
        // nothing to do.
    }
}
