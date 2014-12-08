package com.andrewreitz.onthisday.data.api.archive.model;

import java.io.Serializable;

public final class FileData implements Serializable {
  private final String source;
  private final String creator;
  private final String title;
  private final String track;
  private final String album;
  private final int bitrate;
  private final String length;
  private final String format;
  private final long size;
  private final String md5;
  private final String crc32;
  private final String sha1;

  private FileData(String source, String creator, String title, String track, String album,
      int bitrate, String length, String format, long size, String md5, String crc32, String sha1) {
    this.source = source;
    this.creator = creator;
    this.title = title;
    this.track = track;
    this.album = album;
    this.bitrate = bitrate;
    this.length = length;
    this.format = format;
    this.size = size;
    this.md5 = md5;
    this.crc32 = crc32;
    this.sha1 = sha1;
  }

  public String getSource() {
    return source;
  }

  public String getCreator() {
    return creator;
  }

  public String getTitle() {
    return title;
  }

  public String getTrack() {
    return track;
  }

  public String getAlbum() {
    return album;
  }

  public int getBitrate() {
    return bitrate;
  }

  public String getLength() {
    return length;
  }

  public String getFormat() {
    return format;
  }

  public long getSize() {
    return size;
  }

  public String getMd5() {
    return md5;
  }

  public String getCrc32() {
    return crc32;
  }

  public String getSha1() {
    return sha1;
  }
}
