package com.andrewreitz.onthisday.data.api.reddit.model;

public final class Reddit {
  private final String kind;
  private final Listing data;

  private Reddit(String kind, Listing data) {
    this.kind = kind;
    this.data = data;
  }

  public String getKind() {
    return kind;
  }

  public Listing getData() {
    return data;
  }
}
