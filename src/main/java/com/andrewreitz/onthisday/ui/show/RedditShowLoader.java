package com.andrewreitz.onthisday.ui.show;

import com.andrewreitz.onthisday.data.OnThisDayRedditRepository;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import hugo.weaving.DebugLog;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/** Loads shows from reddit. */
@Singleton
final class RedditShowLoader {
  private final OnThisDayRedditRepository onThisDayRedditRepository;

  @Inject RedditShowLoader(OnThisDayRedditRepository onThisDayRedditRepository) {
    this.onThisDayRedditRepository = onThisDayRedditRepository;
  }

  @DebugLog
  public Subscription loadData(final Observer<List<Data>> observer) {
    return onThisDayRedditRepository.loadReddit() //
        .toList() //
        .subscribeOn(Schedulers.io()) //
        .observeOn(AndroidSchedulers.mainThread()) //
        .subscribe(observer);
  }

  public Subscription loadMoreData(String name, int page, Observer<List<Data>> observer) {
    return onThisDayRedditRepository.loadReddit(name,
        page * OnThisDayRedditRepository.COUNT_INCREMENT) //
        .toList() //
        .subscribeOn(Schedulers.io()) //
        .observeOn(AndroidSchedulers.mainThread()) //
        .subscribe(observer);
  }
}
