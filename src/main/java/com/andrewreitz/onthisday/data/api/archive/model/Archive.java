package com.andrewreitz.onthisday.data.api.archive.model;

import android.net.Uri;
import java.io.Serializable;
import java.util.Map;
import rx.Observable;

import static java.util.Map.Entry;

public final class Archive implements Serializable {
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

  public Uri getArchiveUrl() {
    return new Uri.Builder().scheme("http")
        .encodedAuthority(server)
        .encodedPath(dir)
        .build();
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

  public Observable<Entry<String, FileData>> getFiles() {
    return Observable.from(files.entrySet());
  }

  public Map<String, String> getMisc() {
    return misc;
  }
}
