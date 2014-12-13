package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.reddit.OnThisDayRedditService;
import com.andrewreitz.onthisday.data.api.reddit.model.Child;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.data.api.reddit.model.Listing;
import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;
import hugo.weaving.DebugLog;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Repository specifically looking for on this day posts from reddit.
 */
@Singleton public class OnThisDayRedditRepository {
  /** Reddit's count query string always increments by 25 */
  public static final int COUNT_INCREMENT = 25;

  private static final String EXPECTED_DOMAIN = "archive.org";
  private static final Pattern EXPECTED_DATE_PATTERN =
      Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}");

  private final OnThisDayRedditService onThisDayRedditService;

  @Inject OnThisDayRedditRepository(OnThisDayRedditService onThisDayRedditService) {
    this.onThisDayRedditService = onThisDayRedditService;
  }

  @DebugLog
  public Observable<Data> loadReddit() {
    return applyFilters(onThisDayRedditService.getPosts());
  }

  @DebugLog
  public Observable<Data> loadReddit(String name, int count) {
    return applyFilters(onThisDayRedditService.getPosts(name, count));
  }

  @DebugLog
  private Observable<Data> applyFilters(Observable<Reddit> observable) {
    return observable.map(new Func1<Reddit, Listing>() {
      @Override public Listing call(Reddit reddit) {
        return reddit.getData();
      }
    }).flatMap(new Func1<Listing, Observable<Child>>() {
      @Override public Observable<Child> call(Listing listing) {
        return Observable.from(listing.getChildren());
      }
    }).map(new Func1<Child, Data>() {
      @Override public Data call(Child child) {
        return child.getData();
      }
    }).filter(new Func1<Data, Boolean>() {
      @Override public Boolean call(Data data) {
        return data.getDomain().equals(EXPECTED_DOMAIN);
      }
    }).filter(new Func1<Data, Boolean>() {
      @Override public Boolean call(Data data) {
        return EXPECTED_DATE_PATTERN.matcher(data.getTitle()).find();
      }
    });
  }
}
