package com.andrewreitz.onthisday.ui.show;

import com.andrewreitz.onthisday.data.ArchiveRepository;
import com.andrewreitz.onthisday.data.OnThisDayRedditRepository;
import com.andrewreitz.onthisday.data.RedditArchivePair;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import hugo.weaving.DebugLog;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import timber.log.Timber;

/**
 * Loads shows from reddit and map them to archive.org and combine them into a single data
 * object.
 */
@Singleton
final class RedditToArchiveShowLoader {
  private final OnThisDayRedditRepository onThisDayRedditRepository;
  private final ArchiveRepository archiveRepository;

  private Func1<Data, Observable<RedditArchivePair>> redditToArchive =
      new Func1<Data, Observable<RedditArchivePair>>() {
        @Override public Observable<RedditArchivePair> call(final Data data) {
          Subject<Archive, Archive> archiveSubject = ReplaySubject.create();

          archiveRepository.loadShow(data.getUrl(), archiveSubject);

          return archiveSubject.map(new Func1<Archive, RedditArchivePair>() {
            @Override public RedditArchivePair call(Archive archive) {
              return new RedditArchivePair(archive, data);
            }
          });
        }
      };

  @Inject RedditToArchiveShowLoader(OnThisDayRedditRepository onThisDayRedditRepository,
      ArchiveRepository archiveRepository) {
    this.onThisDayRedditRepository = onThisDayRedditRepository;
    this.archiveRepository = archiveRepository;
  }

  public Subscription loadData(final Observer<List<RedditArchivePair>> observer) {
    return onThisDayRedditRepository.loadReddit() //
        .flatMap(redditToArchive) //
        .toList() //
        .subscribeOn(Schedulers.io()) //
        .observeOn(AndroidSchedulers.mainThread()) //
        .subscribe(observer);
  }

  public Subscription loadMoreData(String name, int page,
      Observer<List<RedditArchivePair>> observer) {
    return onThisDayRedditRepository.loadReddit(name,
        page * OnThisDayRedditRepository.COUNT_INCREMENT) //
        .flatMap(redditToArchive) //
        .toList() //
        .subscribeOn(Schedulers.io()) //
        .observeOn(AndroidSchedulers.mainThread()) //
        .subscribe(observer);
  }
}
