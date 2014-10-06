package com.andrewreitz.onthisday.ui.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;
import com.andrewreitz.onthisday.ui.screen.PlayerScreen;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Queue;
import javax.inject.Inject;
import mortar.Mortar;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static java.util.Map.Entry;

public class PlayerView extends LinearLayout {
  @Inject PlayerScreen.Presenter presenter;

  @InjectView(R.id.player_play_pause) ImageButton playPauseButton;
  @InjectView(R.id.player_album_cover) AlbumCover albumCover;

  /* Play list */
  private final Queue<Entry<String, FileData>> files;
  private final MediaPlayer mediaPlayer;

  private Uri serverPath;

  public PlayerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    files = Lists.newLinkedList();
    mediaPlayer = new MediaPlayer();
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

  @Override public boolean isInEditMode() {
    return true;
  }

  @OnClick(R.id.player_play_pause) void onPlayPausePress() {
    if (mediaPlayer.isPlaying()) {
      playPauseButton.setImageResource(R.drawable.ic_audio_play);
      mediaPlayer.pause();
    } else {
      playPauseButton.setImageResource(R.drawable.ic_audio_pause);
      mediaPlayer.start();
    }
  }

  private Observable<Void> playFile(final Uri filePath) {
    return Observable.create((Subscriber<? super Void> subscriber) -> {
      try {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(filePath.toString());
        mediaPlayer.prepare();
      } catch (IOException e) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onError(e);
        }
      }

      if (!subscriber.isUnsubscribed()) {
        subscriber.onCompleted();
      }
    });
  }

  public void bindTo(Picasso picasso, Archive archive) {
    albumCover.bindTo(picasso, archive);

    serverPath = new Uri.Builder().scheme("http")
        .encodedAuthority(archive.getServer())
        .encodedPath(archive.getDir())
        .build();

    archive.getFiles().filter(
        entry -> entry.getValue().getFormat().equals("VBR MP3")) // TODO Make enum
        .toList().subscribe(files::addAll);

    final Optional<Uri> filePath = getNextUrl();

    mediaPlayer.setOnCompletionListener(mp -> {
      final Optional<Uri> url = getNextUrl();
      playFile(url, mediaPlayer::start);
    });

    playFile(filePath, () -> { /* Wait for play button to be pressed */ });
  }

  private void playFile(Optional<Uri> filePath, final Action0 action) {
    if (filePath.isPresent()) {
      playFile(filePath.get()) //
          .subscribeOn(Schedulers.computation()) //
          .observeOn(AndroidSchedulers.mainThread())  //
          .subscribe( //
              aVoid -> action.call(), //
              throwable -> Timber.e(throwable, "Error playing music") //
          );
    } else {
      Toast.makeText(getContext(), "No more songs to play", Toast.LENGTH_LONG).show();
    }
  }

  private Optional<Uri> getNextUrl() {
    if (files.size() == 0) {
      return Optional.absent();
    }

    final Entry<String, FileData> file = files.remove();
    return Optional.of(serverPath.buildUpon() //
        .appendEncodedPath(file.getKey()) //
        .build());
  }
}
