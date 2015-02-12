package com.andrewreitz.onthisday.ui;

import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;

public final class RedditArchiveViewModel {
  private final Archive archive;
  private final Reddit reddit;

  public RedditArchiveViewModel(Archive archive, Reddit reddit) {
    this.archive = archive;
    this.reddit = reddit;
  }

  public Archive getArchive() {
    return archive;
  }

  public Reddit getReddit() {
    return reddit;
  }
}
