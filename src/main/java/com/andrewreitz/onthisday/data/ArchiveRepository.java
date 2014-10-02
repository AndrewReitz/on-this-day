package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.archive.ArchiveService;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@Singleton
public class ArchiveRepository extends Repository<String, Archive> {

  // No fancy caches here
  private static final Cache<String, Archive> cache = new Cache<String, Archive>() {
    private final Map<String, Archive> cache = Maps.newLinkedHashMap();
    public Archive put(String key, Archive value) {
      return cache.put(key, value);
    }

    @Override public Archive get(String key) {
      return cache.get(key);
    }
  };

  private final ArchiveService archiveService;

  @Inject public ArchiveRepository(ArchiveService archiveService) {
    super(cache);
    this.archiveService = archiveService;
  }

  @Override protected void doNetworkRequest(String key, PublishSubject<Archive> publishSubject) {
    archiveService.getShowData(key)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(publishSubject);
  }
}
