package com.pps.globant.fittracker.mvp.model;

import android.support.annotation.Nullable;

import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.model.fitness.Exercise;

public class ImageDialogModel {

    private final Thumbnail thumbnail;
    private final Exercise exercise;

    public Exercise getExercise() {
        return exercise;
    }

    public ImageDialogModel(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
        this.exercise = null;
    }
    public ImageDialogModel(Exercise exercise) {
        this.exercise = exercise;
        this.thumbnail = null;
    }

    public String getUrlFullResolution() {
        if (thumbnail == null){
            return exercise.getImage();
        }else{
            return thumbnail.toUrlRequest(Thumbnail.FULL_DETAIL);
        }
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
