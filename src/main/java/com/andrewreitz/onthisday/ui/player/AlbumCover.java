package com.andrewreitz.onthisday.ui.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.util.Strings;
import com.squareup.picasso.Picasso;

public class AlbumCover extends FrameLayout {
  @InjectView(R.id.player_album_title) TextView albumTitle;
  @InjectView(R.id.player_track_title) TextView trackTitle;
  @InjectView(R.id.player_album_image) ImageView albumCover;

  public AlbumCover(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  public void bindTo(Picasso picasso, Archive archive) {
    albumTitle.setText(Strings.join(archive.getMetadata().getTitle()));

    String imageUrl = archive.getMisc().get("image");
    if (imageUrl != null) {
      picasso.load(imageUrl) //
          .fit() //
          .into(albumCover);
    }
  }

  public void setTrackTitle(String trackTitle) {
    this.trackTitle.setText(trackTitle);
  }
}
