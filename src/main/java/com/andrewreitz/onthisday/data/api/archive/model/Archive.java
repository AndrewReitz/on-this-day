package com.andrewreitz.onthisday.data.api.archive.model;

import java.util.Map;
import rx.Observable;

public final class Archive {
  private final String server;
  private final String dir;
  private final Metadata metadata;
  private final Map<String, FileData> files;
  private final Map<String, String> misc;

  private Archive(String server, String dir, Metadata metadata, Map<String, FileData> files,
      Map<String, String> misc) {
    this.server = server;
    this.dir = dir;
    this.metadata = metadata;
    this.files = files;
    this.misc = misc;
  }

  public String getServer() {
    return server;
  }

  public String getDir() {
    return dir;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public Map<String, FileData> getFileMap() {
    return files;
  }

  public Observable<FileData> getFileData() {
    return Observable.from(files.values());
  }

  public Observable<String> getFiles() {
    return Observable.from(files.keySet());
  }

  public Map<String, String> getMisc() {
    return misc;
  }
}
