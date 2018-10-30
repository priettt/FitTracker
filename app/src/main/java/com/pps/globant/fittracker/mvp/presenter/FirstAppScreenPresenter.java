package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.utils.BusProvider;

public class FirstAppScreenPresenter {

    private final AvatarsPresenter avatarsPresenter;

    public FirstAppScreenPresenter(AvatarsPresenter avatarsPresenter) {
        this.avatarsPresenter = avatarsPresenter;
    }

    public void register() {
        BusProvider.register(this, avatarsPresenter);
    }

    public void unregister() {
        BusProvider.unregister(this, avatarsPresenter);
    }
}
