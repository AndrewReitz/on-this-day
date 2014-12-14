package com.andrewreitz.onthisday.data.api.reddit.model;

public final class Reddit {
  private final String kind;
  private final Listing data;

  public Reddit(String kind, Listing data) {
    this.kind = kind;
    this.data = data;
  }

  public String getKind() {
    return kind;
  }

  public Listing getData() {
    return data;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Reddit reddit = (Reddit) o;

    if (data != null ? !data.equals(reddit.data) : reddit.data != null) return false;
    if (kind != null ? !kind.equals(reddit.kind) : reddit.kind != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = kind != null ? kind.hashCode() : 0;
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Reddit{" //
        + "kind='" + kind + '\'' //
        + ", data=" + data //
        + '}';
  }
}
