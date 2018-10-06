package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.adapters.AvatarsAdapter;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.mvp.events.GetAvatarsSuccessEvent;
import com.pps.globant.fittracker.mvp.model.AvatarsModel;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.view.AvatarsView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Subscribe;

public class AvatarsPresenter implements ImageDialog.OnAcceptClickListener {
    public static final String EXTRA_MESSAGE = "com.pps.globant.fittracker.USERID";
    private AvatarsModel model;
    private AvatarsView view;

    public AvatarsPresenter(AvatarsModel model, AvatarsView view, String userId) {
        this.model = model;
        this.view = view;
        init(userId);
    }

    @Override
    public void onAcceptAvatar(Thumbnail thumbnail) {
        model.setThumbnail(thumbnail);
        view.setAvatar(model.getUser().getAvatarThumbnail());
        view.collapseCard();
    }

    public void register() {
        BusProvider.register(this);
    }

    public void unregister() {
        BusProvider.unregister(this);
    }

    private void initList() {
        model.getAvatarsList();
    }

    void init(String userId) {
        model.getUserFromDbById(Long.parseLong(userId));
    }

    @Subscribe
    public void onAvatarsSuccessEvent(GetAvatarsSuccessEvent event) {
        view.setAdapter(new AvatarsAdapter(event.getAvatarsList(), this));
    }

    @Subscribe
    public void onLessDetailsPressedEvent(AvatarsView.LessDetailsPressedEvent event) {
        view.collapseCard();
    }

    @Subscribe
    public void onFetchingUserFromDataBaseCompleted(UsersRepository.FetchingUserFromDataBaseCompleted event) {
        if (event.user == null) {
            //should never happen
            return;
        }
        model.setUser(event.user);
        view.setFirstName(event.user.getFirstName());
        if (event.user.getAvatarThumbnail() == null) {
            view.setNullAvatar();
        } else {
            view.setAvatar(event.user.getAvatarThumbnail());
        }
    }

    @Subscribe
    public void onChangeAvatarPressedEvent(AvatarsView.ChangeAvatarPressedEvent event) {
        view.expandCard();
        initList();
    }

}
