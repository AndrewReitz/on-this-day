package com.andrewreitz.onthisday.ui.show;

import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import java.util.List;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import shillelagh.Shillelagh;

/** Loader for items that have been stared. */
final class StarredLoader implements ShowListView.DataLoader {
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
