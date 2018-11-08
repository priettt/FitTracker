package com.pps.globant.fittracker.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.adapters.ExerciseAdapter;
import com.pps.globant.fittracker.model.fitness.Exercise;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseView extends ActivityView<FirstAppScreenActivity> {
    @BindView(R.id.recycler_exercise)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_exercise2)
    ProgressBar progressBar;

    public ExerciseView(FirstAppScreenActivity activity) {
        super(activity);
        ButterKnife.bind(this,activity);
        recyclerView.setAdapter(new ExerciseAdapter(new ArrayList<Exercise>()));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
    }

    public void setAdapter(ExerciseAdapter exerciseAdapter) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.invalidate();
    }
}

