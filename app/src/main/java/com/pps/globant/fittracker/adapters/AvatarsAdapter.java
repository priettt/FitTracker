package com.pps.globant.fittracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.globant.fittracker.ImageDialog;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.model.avatars.Avatar;
import com.pps.globant.fittracker.model.avatars.Thumbnail;
import com.pps.globant.fittracker.utils.ImageLoadedCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvatarsAdapter extends RecyclerView.Adapter<AvatarsAdapter.AvatarsViewHolder> {

    public static final int CERO = 0;
    ImageDialog.OnAcceptClickListener onAcceptClickListener;
    private List<Avatar> avatars;

    public AvatarsAdapter(List<Avatar> avatars, ImageDialog.OnAcceptClickListener onAcceptClickListener) {
        this.avatars = avatars;
        this.onAcceptClickListener = onAcceptClickListener;
    }

    public AvatarsAdapter(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    @Override
    public AvatarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_layout, parent, false);
        return new AvatarsViewHolder(view, onAcceptClickListener);
    }

    @Override
    public void onBindViewHolder(final AvatarsViewHolder viewHolder, int position) {
        final Avatar imageInfo = avatars.get(position);

        viewHolder.name.setText(imageInfo.getName());
        viewHolder.thumbnail = imageInfo.getThumbnail();
        Picasso.with(viewHolder.image.getContext()).
                load(imageInfo.getThumbnail().toUrlRequest(Thumbnail.STANDARD_XLARGE))
                .into(viewHolder.image, new ImageLoadedCallback(viewHolder.progressBar) {
            @Override
            public void onSuccess() {
                if (viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return avatars != null ? avatars.size() : CERO;
    }

    public static class AvatarsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_thumbnail_avatar) ImageView image;
        @BindView(R.id.image_avatar_id) TextView name;
        @BindView(R.id.progress_bar_avatar_small) ProgressBar progressBar;
        Thumbnail thumbnail;
        private ImageDialog.OnAcceptClickListener onAcceptClickListener;

        public AvatarsViewHolder(View itemView, ImageDialog.OnAcceptClickListener onAcceptClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onAcceptClickListener = onAcceptClickListener;
        }

        @OnClick(R.id.image_thumbnail_avatar)
        public void onImageClick(View view) {
            new ImageDialog(view.getContext(), thumbnail, onAcceptClickListener).show();
        }
    }
}