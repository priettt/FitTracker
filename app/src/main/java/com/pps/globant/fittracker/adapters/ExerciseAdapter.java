package com.pps.globant.fittracker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.model.fitness.Exercise;
import com.pps.globant.fittracker.utils.ImageLoadedCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{
    private List<Exercise> ejercicios;
    public static final int CERO = 0;


    public ExerciseAdapter(List<Exercise> ejercicios) {
        this.ejercicios = ejercicios;
    }


    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_layout,parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder exerciseViewHolder, int i) {
    final Exercise ejercicio = ejercicios.get(i);
        Picasso.with(exerciseViewHolder.image.getContext()).
                load(ejercicio.getImage())
                .into(exerciseViewHolder.image,new ImageLoadedCallback(exerciseViewHolder.progressBar){
                    @Override
                    public void onSuccess() {
                        if (exerciseViewHolder.progressBar != null) {
                            exerciseViewHolder.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ejercicios != null ? ejercicios.size() : 0;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_exercise)
        ImageView image;
        @BindView(R.id.progress_bar_exercise)
        ProgressBar progressBar;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
