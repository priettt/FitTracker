package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.model.avatars.Thumbnail;

public class ImageDialogModel {

    private final Thumbnail thumbnail;

    public ImageDialogModel(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrlFullResolution() {
        return thumbnail.toUrlRequest(Thumbnail.FULL_DETAIL);
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
