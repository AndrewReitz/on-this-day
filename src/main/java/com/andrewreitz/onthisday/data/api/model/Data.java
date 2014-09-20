package com.andrewreitz.onthisday.data.api.model;

import com.google.gson.annotations.SerializedName;

public final class Data {
  private final String selftext;
  private final String id;
  private final String thumbnail;
  private final String permalink;
  private final String name;
  private final float created;
  private final String url;
  private final String title;

  @SerializedName("num_comments")
  private final int numComments;

  @SerializedName("created_utc")
  private final float createdUtc;

  public Data(String selftext, String id, String thumbnail, String permalink, String name,
              float created, String url, String title, int numComments, float createdUtc) {
    this.selftext = selftext;
    this.id = id;
    this.thumbnail = thumbnail;
    this.permalink = permalink;
    this.name = name;
    this.created = created;
    this.url = url;
    this.title = title;
    this.numComments = numComments;
    this.createdUtc = createdUtc;
  }

  public String getSelftext() {
    return selftext;
  }

  public String getId() {
    return id;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public String getPermalink() {
    return permalink;
  }

  public String getName() {
    return name;
  }

  public float getCreated() {
    return created;
  }

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public int getNumComments() {
    return numComments;
  }

  public float getCreatedUtc() {
    return createdUtc;
  }
}
