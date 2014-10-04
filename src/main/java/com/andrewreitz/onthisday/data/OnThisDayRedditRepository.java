package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.reddit.OnThisDayRedditService;
import com.andrewreitz.onthisday.data.api.reddit.model.Child;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

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

  public Observable<Data> loadReddit() {
    return applyFilters(onThisDayRedditService.getPosts());
  }

  public Observable<Data> loadReddit(String name, int count) {
    return applyFilters(onThisDayRedditService.getPosts(name, count));
  }

  private Observable<Data> applyFilters(Observable<Reddit> observable) {
    return observable.map(Reddit::getData)
        .flatMap(listing -> Observable.from(listing.getChildren()))
        .map(Child::getData)
        .filter(data -> data.getDomain().equals(EXPECTED_DOMAIN))
        .filter(data -> EXPECTED_DATE_PATTERN.matcher(data.getTitle()).find());
  }
}
