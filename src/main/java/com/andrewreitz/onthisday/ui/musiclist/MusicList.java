package com.andrewreitz.onthisday.ui.musiclist;

import android.os.Bundle;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.RedditRepository;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

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
    private final RedditRepository redditRepository;

    private Subscription request = Subscriptions.empty();

    @Inject Presenter(@MainScope Flow flow, RedditRepository redditRepository) {
      this.flow = flow;
      this.redditRepository = redditRepository;
    }

    @Override public void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
      final MusicListView view = getView();

      request = redditRepository.loadReddit()
          .toList()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(shows -> {
            view.getShows().add(shows);
            view.hideSpinner();
          });

      view.setLoadMoreListener((name, page) -> request =
          redditRepository.loadReddit(name, page * RedditRepository.COUNT_INCREMENT)
              .toList()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(shows -> view.getShows().add(shows)));
    }

    public void onShowSelected(Data show) {

    }

    public void visibilityChanged(boolean visible) {
      if (!visible) {
        request.unsubscribe();
      }
    }
  }
}
