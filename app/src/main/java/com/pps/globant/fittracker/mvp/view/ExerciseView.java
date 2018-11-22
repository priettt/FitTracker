package com.pps.globant.fittracker.mvp.view;

import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.adapters.ExerciseAdapter;
import com.pps.globant.fittracker.model.fitness.Exercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseView extends ActivityView<FirstAppScreenActivity> {
    private static final int SPAN_COUNT = 3;
    @BindView(R.id.recycler_exercise)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_exercise2)
    ProgressBar progressBar;

    public ExerciseView(FirstAppScreenActivity activity) {
        super(activity);
        ButterKnife.bind(this,activity);
        recyclerView.setAdapter(new ExerciseAdapter(new ArrayList<Exercise>()));
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(manager);
    }

    public void setAdapter(ExerciseAdapter exerciseAdapter) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.invalidate();
    }

}

