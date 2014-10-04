package com.andrewreitz.onthisday.ui.screen;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.show.ShowListView;
import dagger.Provides;
import flow.Layout;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import mortar.Blueprint;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import shillelagh.Shillelagh;
import timber.log.Timber;

@Layout(R.layout.view_show_list)
public class StarredScreen implements Blueprint, IsMain {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new StarredScreen.Module();
  }

  @dagger.Module(
      injects = ShowListView.class, //
      addsTo = Main.Module.class, //
      complete = false) //
  class Module {
    @Provides @Singleton ShowListPresenter.DataLoader provideStarredLoader(Shillelagh shillelagh) {
      return new StarredLoader(shillelagh);
    }
  }

  static class StarredLoader implements ShowListPresenter.DataLoader {

    private Shillelagh shillelagh;

    StarredLoader(Shillelagh shillelagh) {
      this.shillelagh = shillelagh;
    }

    @Override public Subscription loadData(Observer<List<Data>> observer) {
      return shillelagh.get(Data.class)
          .buffer(10)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(observer);
    }

    @Override public Subscription loadMoreData(String name, int page, Observer<List<Data>> observer) {
      return Subscriptions.empty();
    }
  }
}
