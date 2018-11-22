package com.pps.globant.fittracker.mvp.model;
import com.pps.globant.fittracker.model.UrlGetter;
import com.pps.globant.fittracker.model.avatars.Thumbnail;

public class ImageDialogModel {

    private UrlGetter urlGetter;

    public String getUrlFullResolution() {
        return urlGetter.urlGetter();
    }

    public void setUrlGetter(UrlGetter urlGetter) {
        this.urlGetter = urlGetter;
    }

    public UrlGetter getUrlGetter() {
        return urlGetter;
    }

    public Thumbnail getThumbnail() {
        return (Thumbnail)urlGetter;
    }
}
