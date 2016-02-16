package com.andrewreitz.onthisday.mediaplayer.event;

public final class PlayStateEvent {
  public final boolean isPlaying;

  public PlayStateEvent(boolean isPlaying) {
    this.isPlaying = isPlaying;
  }
}
