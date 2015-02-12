package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;

import static com.google.common.base.Preconditions.checkNotNull;

/** Data object that contains archive data and reddit data. */
public final class RedditArchivePair {
  private final Archive archive;
  private final Data data;

  /**
   * Constructor.
   *
   * @param archive Archive data from archive.org
   * @param data Data from reddit.
   */
  public RedditArchivePair(Archive archive, Data data) {
    this.archive = checkNotNull(archive, "Archive may not be null.");
    this.data  = checkNotNull(data, "Data may not be null.");
  }

  public Archive getArchiveData() {
    return archive;
  }

  public Data getRedditData() {
    return data;
  }
}
