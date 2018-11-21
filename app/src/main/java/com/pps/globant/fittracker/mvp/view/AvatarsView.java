package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.adapters.AvatarsAdapter;
import com.pps.globant.fittracker.model.avatars.Avatar;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.utils.ImageLoadedCallback;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvatarsView extends CardViewView {

    public static final boolean REVERSE_LAYOUT_FALSE = false;
    public static final int SPAN_COUNT = 2;
    @BindView(R.id.layout_cards_container) ConstraintLayout layoutCardsContainer;

    @BindView(R.id.layout_avatars_card_expanded) ConstraintLayout layoutExpanded;
    @BindView(R.id.recycler_avatars) RecyclerView recyclerView;
    @BindView(R.id.progress_bar_avatars) ProgressBar progressBarAvatars;

    @BindView(R.id.layout_avatars_card_collapsed) ConstraintLayout layoutCollapsed;
    @BindView(R.id.progress_bar_actual_avatar) ProgressBar progressBarActualAvatar;
    @BindView(R.id.image_actual_avatar) ImageView imageActualAvatar;

    @BindView(R.id.text_first_name) TextView textFirstName;
    @BindView(R.id.progress_bar_first_name) ProgressBar progressBarFirstName;

    private Bus bus;

    public AvatarsView(CardView cardView, Activity activity, Bus bus) {
        super(cardView, activity);
        ButterKnife.bind(this, activity);
        this.bus = bus;

    }

    @OnClick(R.id.image_actual_avatar)
    public void changeAvatarPressed() {
        bus.post(new ChangeAvatarPressedEvent());
    }

    @OnClick(R.id.text_cancel_avatars_list)
    public void lessDetailsPressed() {
        bus.post(new LessDetailsPressedEvent());
    }

    public void setAdapter(AvatarsAdapter adapter) {
        progressBarAvatars.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }

    public void expandCard() {
        TransitionManager.beginDelayedTransition(layoutCardsContainer, new AutoTransition());
        layoutCollapsed.setVisibility(View.GONE);
        layoutExpanded.setVisibility(View.VISIBLE);
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new AvatarsAdapter(new ArrayList<Avatar>()));
            GridLayoutManager glm = new GridLayoutManager(getActivity(), SPAN_COUNT,
                    GridLayoutManager.HORIZONTAL, REVERSE_LAYOUT_FALSE);
            recyclerView.setLayoutManager(glm);
        }
    }

    public void collapseCard() {
        TransitionManager.beginDelayedTransition(layoutCardsContainer, new AutoTransition());
        layoutExpanded.setVisibility(View.GONE);
        layoutCollapsed.setVisibility(View.VISIBLE);
    }

    public void setNullAvatar() {
        progressBarActualAvatar.setVisibility(View.GONE);
    }

    public void setAvatar(Thumbnail avatarThumbnail) {
        progressBarActualAvatar.setVisibility(View.VISIBLE);
        Picasso.get().
                load(avatarThumbnail.toUrlRequest(Thumbnail.STANDARD_LARGE)).into(imageActualAvatar, new
                ImageLoadedCallback(progressBarActualAvatar) {
                    @Override
                    public void onSuccess() {
                        if (progressBarActualAvatar != null) {
                            progressBarActualAvatar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void setFirstName(String firstName) {
        textFirstName.setText(firstName);
        progressBarFirstName.setVisibility(View.GONE);
    }

    public class ChangeAvatarPressedEvent {
    }

    public class LessDetailsPressedEvent {
    }
}
