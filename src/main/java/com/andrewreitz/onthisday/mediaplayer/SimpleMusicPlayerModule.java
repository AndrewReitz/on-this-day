package com.andrewreitz.onthisday.mediaplayer;

import android.media.MediaPlayer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(injects = SimpleMusicPlayerService.class, complete = false)
public class SimpleMusicPlayerModule {
  @Provides @Singleton MediaPlayer provideMediaPlayer() {
    return new MediaPlayer();
  }
}
