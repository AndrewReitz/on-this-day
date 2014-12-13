package com.andrewreitz.onthisday.ui.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.mediaplayer.event.NextEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PauseEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PlayEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PlayStateEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PreviousEvent;
import com.andrewreitz.onthisday.mediaplayer.event.TitleChangeEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class PlayerView extends LinearLayout {
  @Inject Bus bus;

  @InjectView(R.id.player_play_pause) ImageButton playPauseButton;
  @InjectView(R.id.player_album_cover) AlbumCover albumCover;

  private boolean isPlaying = false;

  public PlayerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    bus.register(this);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    bus.unregister(this);
  }

  @OnClick(R.id.player_play_previous) void onPreviousPressed() {
    bus.post(new PreviousEvent());
  }

  @OnClick(R.id.player_play_pause) void onPlayPausePressed() {
    if (!isPlaying) {
      bus.post(new PlayEvent());
    } else {
      bus.post(new PauseEvent());
    }
  }

  @OnClick(R.id.player_play_next) void onNextPressed() {
    bus.post(new NextEvent());
  }

  @Subscribe public void onTitleChangeEvent(TitleChangeEvent event) {
    albumCover.setTrackTitle(event.title);
  }

  @Subscribe public void onPlayStateEvent(PlayStateEvent event) {
    if (isPlaying = event.isPlaying) {
      playPauseButton.setImageResource(R.drawable.ic_audio_pause);
    } else {
      playPauseButton.setImageResource(R.drawable.ic_audio_play);
    }
  }

  public void bindTo(Picasso picasso, Archive archive) {
    albumCover.bindTo(picasso, archive);
  }
}
