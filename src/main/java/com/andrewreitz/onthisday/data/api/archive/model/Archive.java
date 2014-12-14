package com.andrewreitz.onthisday.data.api.archive.model;

import android.net.Uri;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import rx.Observable;

import static java.util.Map.Entry;

public final class Archive implements Serializable {
  private final String server;
  private final String dir;
  private final Metadata metadata;
  private final Map<String, FileData> files;
  private final Map<String, String> misc;

  public Archive(String server, String dir, Metadata metadata, Map<String, FileData> files,
      Map<String, String> misc) {
    this.server = server;
    this.dir = dir;
    this.metadata = metadata;
    this.files = files;
    this.misc = misc;
  }

  public Uri getArchiveUrl() {
    return new Uri.Builder() //
        .scheme("http") //
        .encodedAuthority(server) //
        .encodedPath(dir) //
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
    return Collections.unmodifiableMap(files);
  }

  public Observable<FileData> getFileData() {
    return Observable.from(files.values());
  }

  public Observable<Entry<String, FileData>> getFiles() {
    return Observable.from(files.entrySet());
  }

  public Map<String, String> getMisc() {
    return Collections.unmodifiableMap(misc);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Archive archive = (Archive) o;

    if (dir != null ? !dir.equals(archive.dir) : archive.dir != null) return false;
    if (files != null ? !files.equals(archive.files) : archive.files != null) return false;
    if (metadata != null ? !metadata.equals(archive.metadata) : archive.metadata != null) {
      return false;
    }
    if (misc != null ? !misc.equals(archive.misc) : archive.misc != null) return false;
    if (server != null ? !server.equals(archive.server) : archive.server != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = server != null ? server.hashCode() : 0;
    result = 31 * result + (dir != null ? dir.hashCode() : 0);
    result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
    result = 31 * result + (files != null ? files.hashCode() : 0);
    result = 31 * result + (misc != null ? misc.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Archive{" //
        + "server='" + server + '\'' //
        + ", dir='" + dir + '\'' //
        + ", metadata=" + metadata //
        + ", files=" + files //
        + ", misc=" + misc //
        + '}';
  }
}
