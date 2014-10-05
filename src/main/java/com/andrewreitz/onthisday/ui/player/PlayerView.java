package com.andrewreitz.onthisday.ui.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.ui.screen.PlayerScreen;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import mortar.Mortar;

public class PlayerView extends LinearLayout {
  @Inject PlayerScreen.Presenter presenter;

  @InjectView(R.id.player_album_cover) AlbumCover albumCover;

  public PlayerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    presenter.takeView(this);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenter.dropView(this);
  }

  public void bindTo(Picasso picasso, Archive archive) {
    albumCover.bindTo(picasso, archive);
  }
}
