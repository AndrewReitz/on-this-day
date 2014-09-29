package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.reddit.RedditService;
import com.andrewreitz.onthisday.data.api.reddit.model.Child;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton
public class RedditRepository {
  /** Reddit's count query string always increments by 25 */
  public static final int COUNT_INCREMENT = 25;

  private static final String EXPECTED_DOMAIN = "archive.org";
  private static final Pattern EXPECTED_DATE_PATTERN =
      Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}");

  private final RedditService redditService;

  @Inject RedditRepository(RedditService redditService) {
    this.redditService = redditService;
  }

  public Observable<Data> loadReddit() {
    return redditService.getPosts()
        .map(Reddit::getData)
        .flatMap(listing -> Observable.from(listing.getChildren()))
        .map(Child::getData)
        .filter(data -> data.getDomain().equals(EXPECTED_DOMAIN))
        .filter(data -> EXPECTED_DATE_PATTERN.matcher(data.getTitle()).find());
  }

  public Observable<Data> loadReddit(String name, int count) {
    return redditService.getPosts(name, count)
        .map(Reddit::getData)
        .flatMap(listing -> Observable.from(listing.getChildren()))
        .map(Child::getData)
        .filter(data -> data.getDomain().equals(EXPECTED_DOMAIN))
        .filter(data -> EXPECTED_DATE_PATTERN.matcher(data.getTitle()).find());
  }
}
