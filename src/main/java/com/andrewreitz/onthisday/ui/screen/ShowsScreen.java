package com.andrewreitz.onthisday.ui.screen;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.OnThisDayRedditRepository;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.show.ShowListView;
import dagger.Provides;
import flow.Layout;
import java.util.List;
import javax.inject.Singleton;
import mortar.Blueprint;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Layout(R.layout.show_list_view)
public class ShowsScreen implements Blueprint, IsMain {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      injects = ShowListView.class, //
      addsTo = Main.Module.class, //
      complete = false) //
  class Module {
    @Provides @Singleton ShowListPresenter.DataLoader provideShowLoader(
        OnThisDayRedditRepository repository) {
      return new RedditShowLoader(repository);
    }
  }

  static class RedditShowLoader implements ShowListPresenter.DataLoader {
    private final OnThisDayRedditRepository onThisDayRedditRepository;

    RedditShowLoader(OnThisDayRedditRepository onThisDayRedditRepository) {
      this.onThisDayRedditRepository = onThisDayRedditRepository;
    }

    @Override public Subscription loadData(Observer<List<Data>> observer) {
      return onThisDayRedditRepository.loadReddit()
          .toList()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(observer);
    }

    @Override public Subscription loadMoreData(String name, int page, Observer<List<Data>> observer) {
      return onThisDayRedditRepository.loadReddit(name,
          page * OnThisDayRedditRepository.COUNT_INCREMENT)
          .toList()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(observer);
    }
  }
}
