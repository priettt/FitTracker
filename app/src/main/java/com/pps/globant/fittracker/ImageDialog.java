package com.pps.globant.fittracker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.model.fitness.Exercise;
import com.pps.globant.fittracker.mvp.model.ImageDialogModel;
import com.pps.globant.fittracker.mvp.presenter.ImageDialogPresenter;
import com.pps.globant.fittracker.mvp.view.ImageDialogView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

public class ImageDialog extends Dialog {
    private ImageDialogPresenter presenter;
    private Thumbnail thumbnail;
    private OnAcceptClickListener onAcceptClickListener;
    private OnAcceptClickListenerForExercise onAcceptClickListenerForExercise;
    private Exercise exercise;

    public ImageDialog(Context context, Thumbnail thumbnail, OnAcceptClickListener onAcceptClickListener) {
        super(context);
        this.thumbnail = thumbnail;
        this.onAcceptClickListener = onAcceptClickListener;
    }
    public ImageDialog(Context context,Exercise exercise, OnAcceptClickListenerForExercise onAcceptClickListenerForExercise) {
        super(context);
        this.exercise = exercise;
        this.onAcceptClickListenerForExercise = onAcceptClickListenerForExercise;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_avatar);
        ButterKnife.bind(this);
        setTitle(null);
        setCancelable(false);
        Bus bus = BusProvider.getInstance();
        if (thumbnail != null){
            presenter = new ImageDialogPresenter(new ImageDialogView(this, bus), new ImageDialogModel(thumbnail), onAcceptClickListener);
        }else{
            presenter = new ImageDialogPresenter(new ImageDialogView(this, bus), new ImageDialogModel(exercise), onAcceptClickListenerForExercise);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.register(presenter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.unregister(presenter);
    }

    public interface OnAcceptClickListener {
        void onAcceptAvatar(Thumbnail thumbnail);
    }
    public interface OnAcceptClickListenerForExercise {
        void onAcceptExercise(Exercise exercise);
    }
}