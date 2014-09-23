package com.andrewreitz.onthisday.ui.musiclist;

import android.os.Bundle;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;

@Layout(R.layout.view_music_list)
public class MusicList implements Blueprint, IsMain {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      injects = MusicListView.class,
      addsTo = Main.Module.class,
      complete = false
  )
  static class Module {
  }

  @Singleton
  public static class Presenter extends ViewPresenter<MusicListView> {
    private final Flow flow;

    @Inject Presenter(@MainScope Flow flow) {
      this.flow = flow;
    }

    @Override public void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
    }
  }
}
