package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.archive.ArchiveService;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.velcro.rx.EndObserver;
import com.google.common.collect.Maps;
import java.util.Map;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.Subscriptions;

public class ArchiveRepository {
  private final ArchiveService archiveService;

  private final Map<String, Archive> cache = Maps.newLinkedHashMap();
  private final Map<String, PublishSubject<Archive>> requests = Maps.newLinkedHashMap();

  public ArchiveRepository(ArchiveService archiveService) {
    this.archiveService = archiveService;
  }

  public Subscription loadShow(final String showUrl, final Observer<Archive> observer) {
    Archive item = cache.get(showUrl);
    if (item != null) {
      // We have a cached value. Emit it immediately.
      observer.onNext(item);
      // Don't do another network all.
      return Subscriptions.empty();
    }

    PublishSubject<Archive> request = requests.get(showUrl);
    if (request != null) {
      // There's an in-flight network request for this section already. Join it.
      return request.subscribe(observer);
    }

    request = PublishSubject.create();
    requests.put(showUrl, request);

    Subscription subscription = request.subscribe(observer);

    request.subscribe(new EndObserver<Archive>() {
      @Override public void onEnd() {
        requests.remove(showUrl);
      }

      @Override public void onNext(Archive item) {
        cache.put(showUrl, item);
      }
    });

    archiveService.getShowData(showUrl)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(request);

    return subscription;
  }
}
