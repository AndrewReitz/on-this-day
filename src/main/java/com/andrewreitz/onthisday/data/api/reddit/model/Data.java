package com.andrewreitz.onthisday.data.api.reddit.model;

import com.google.gson.annotations.SerializedName;
import shillelagh.Field;
import shillelagh.Id;
import shillelagh.OrmOnly;
import shillelagh.Table;

@Table
public final class Data {
  @Id long _id;
  @Field String domain;
  @Field String selftext;
  @Field String id;
  @Field String thumbnail;
  @Field String permalink;
  @Field String name;
  @Field float created;
  @Field String url;
  @Field String title;

  @SerializedName("num_comments") @Field int numComments;

  @SerializedName("created_utc") @Field float createdUtc;

  @OrmOnly Data() {
  }

  public String getDomain() {
    return domain;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Data data = (Data) o;

    if (Float.compare(data.created, created) != 0) return false;
    if (Float.compare(data.createdUtc, createdUtc) != 0) return false;
    if (numComments != data.numComments) return false;
    if (domain != null ? !domain.equals(data.domain) : data.domain != null) return false;
    if (id != null ? !id.equals(data.id) : data.id != null) return false;
    if (name != null ? !name.equals(data.name) : data.name != null) return false;
    if (permalink != null ? !permalink.equals(data.permalink) : data.permalink != null) {
      return false;
    }
    if (selftext != null ? !selftext.equals(data.selftext) : data.selftext != null) return false;
    if (thumbnail != null ? !thumbnail.equals(data.thumbnail) : data.thumbnail != null) {
      return false;
    }
    if (title != null ? !title.equals(data.title) : data.title != null) return false;
    if (url != null ? !url.equals(data.url) : data.url != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = domain != null ? domain.hashCode() : 0;
    result = 31 * result + (selftext != null ? selftext.hashCode() : 0);
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
    result = 31 * result + (permalink != null ? permalink.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (created != +0.0f ? Float.floatToIntBits(created) : 0);
    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + numComments;
    result = 31 * result + (createdUtc != +0.0f ? Float.floatToIntBits(createdUtc) : 0);
    return result;
  }

  @Override public String toString() {
    return "Data{" //
        + "domain='" + domain + '\'' //
        + ", selftext='" + selftext + '\'' //
        + ", id='" + id + '\'' //
        + ", thumbnail='" + thumbnail + '\'' //
        + ", permalink='" + permalink + '\'' //
        + ", name='" + name + '\'' //
        + ", created=" + created //
        + ", url='" + url + '\'' //
        + ", title='" + title + '\'' //
        + ", numComments=" + numComments //
        + ", createdUtc=" + createdUtc //
        + '}';
  }
}
