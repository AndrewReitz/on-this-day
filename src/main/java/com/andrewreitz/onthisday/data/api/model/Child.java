package com.andrewreitz.onthisday.data.api.model;

public final class Child {
  private final String kind;
  private final Data data;

  private Child(String kind, Data data) {
    this.kind = kind;
    this.data = data;
  }

  public String getKind() {
    return kind;
  }

  public Data getData() {
    return data;
  }
}
