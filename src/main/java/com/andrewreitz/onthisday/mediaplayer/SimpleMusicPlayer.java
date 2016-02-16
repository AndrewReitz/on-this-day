package com.andrewreitz.onthisday.mediaplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import timber.log.Timber;

@Singleton
public class SimpleMusicPlayer {

  private final MediaPlayer mediaPlayer;

  @Inject SimpleMusicPlayer(MediaPlayer mediaPlayer) {
    this.mediaPlayer = mediaPlayer;
  }

  void setOnCompletionListener(final Action0 action) {
    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override public void onCompletion(MediaPlayer mp) {
        action.call();
      }
    });
  }

  Observable<Void> playFile(final Uri filePath) {
    return Observable.create(new Observable.OnSubscribe<Void>() {
      @Override public void call(Subscriber<? super Void> subscriber) {
        try {
          mediaPlayer.reset();
          mediaPlayer.setDataSource(filePath.toString());
          mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          mediaPlayer.prepare();
        } catch (IOException e) {
          if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
          }
        }

        if (!subscriber.isUnsubscribed()) {
          subscriber.onCompleted();
        }
      }
    });
  }

  void play() {
    mediaPlayer.start();
  }

  void pause() {
    mediaPlayer.pause();
  }

  boolean isPlaying() {
    return mediaPlayer.isPlaying();
  }
}
