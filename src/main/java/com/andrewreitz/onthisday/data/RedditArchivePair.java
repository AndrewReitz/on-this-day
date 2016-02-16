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
    this.data = checkNotNull(data, "Data may not be null.");
  }

  public Archive getArchiveData() {
    return archive;
  }

  public Data getRedditData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RedditArchivePair that = (RedditArchivePair) o;

    if (archive != null ? !archive.equals(that.archive) : that.archive != null) return false;
    if (data != null ? !data.equals(that.data) : that.data != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = archive != null ? archive.hashCode() : 0;
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "RedditArchivePair{" //
        + "archive=" + archive //
        + ", data=" + data //
        + '}';
  }
}
