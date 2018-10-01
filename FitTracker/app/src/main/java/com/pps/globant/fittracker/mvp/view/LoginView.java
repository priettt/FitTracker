package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.text_fb_label)
    TextView textFbLabel;
    @BindView(R.id.buttton_fb)
    Button buttonFb;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void setLabelFb(String label) {
        textFbLabel.setText(label);
    }

    @OnClick(R.id.buttton_fb)
    public void fbButtonPressed() {
        bus.post(new FbButtonPressedEvent());
    }

    public void setLabelButtonFb(String label) {
        this.buttonFb.setText(label);
    }

    public void popUp(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public class FbButtonPressedEvent {
    }
}
