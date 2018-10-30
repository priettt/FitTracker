package com.pps.globant.fittracker.mvp.events;

import com.pps.globant.fittracker.model.avatars.Avatar;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.AvatarsData;

import java.util.List;

public class GetAvatarsSuccessEvent {
    private AvatarsData avatarsData;

    public GetAvatarsSuccessEvent(AvatarsData avatarsData) {
        this.avatarsData = avatarsData;
    }

    public List<Avatar> getAvatarsList() {
        return (avatarsData.getData().getResults());
    }
}
