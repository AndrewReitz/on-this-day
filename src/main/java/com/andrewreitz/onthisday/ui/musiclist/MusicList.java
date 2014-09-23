package com.andrewreitz.onthisday.ui.musiclist;

import com.andrewreitz.onthisday.R;

import flow.Layout;
import mortar.Blueprint;

@Layout(R.layout.view_music_list)
public class MusicList implements Blueprint {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return null;
  }
}
