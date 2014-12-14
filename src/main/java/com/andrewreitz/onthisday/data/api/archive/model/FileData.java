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

  public FileData(String source, String creator, String title, String track, String album,
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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FileData fileData = (FileData) o;

    if (bitrate != fileData.bitrate) return false;
    if (size != fileData.size) return false;
    if (album != null ? !album.equals(fileData.album) : fileData.album != null) return false;
    if (crc32 != null ? !crc32.equals(fileData.crc32) : fileData.crc32 != null) return false;
    if (creator != null ? !creator.equals(fileData.creator) : fileData.creator != null) {
      return false;
    }
    if (format != null ? !format.equals(fileData.format) : fileData.format != null) return false;
    if (length != null ? !length.equals(fileData.length) : fileData.length != null) return false;
    if (md5 != null ? !md5.equals(fileData.md5) : fileData.md5 != null) return false;
    if (sha1 != null ? !sha1.equals(fileData.sha1) : fileData.sha1 != null) return false;
    if (source != null ? !source.equals(fileData.source) : fileData.source != null) return false;
    if (title != null ? !title.equals(fileData.title) : fileData.title != null) return false;
    if (track != null ? !track.equals(fileData.track) : fileData.track != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = source != null ? source.hashCode() : 0;
    result = 31 * result + (creator != null ? creator.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (track != null ? track.hashCode() : 0);
    result = 31 * result + (album != null ? album.hashCode() : 0);
    result = 31 * result + bitrate;
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (format != null ? format.hashCode() : 0);
    result = 31 * result + (int) (size ^ (size >>> 32));
    result = 31 * result + (md5 != null ? md5.hashCode() : 0);
    result = 31 * result + (crc32 != null ? crc32.hashCode() : 0);
    result = 31 * result + (sha1 != null ? sha1.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "FileData{" //
        + "source='" + source + '\'' //
        + ", creator='" + creator + '\'' //
        + ", title='" + title + '\'' //
        + ", track='" + track + '\'' //
        + ", album='" + album + '\'' //
        + ", bitrate=" + bitrate //
        + ", length='" + length + '\'' //
        + ", format='" + format + '\'' //
        + ", size=" + size //
        + ", md5='" + md5 + '\'' //
        + ", crc32='" + crc32 + '\'' //
        + ", sha1='" + sha1 + '\'' //
        + '}';
  }
}
