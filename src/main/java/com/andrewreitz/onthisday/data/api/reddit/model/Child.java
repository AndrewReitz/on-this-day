package com.andrewreitz.onthisday.data.api.reddit.model;

public final class Child {
  private final String kind;
  private final Data data;

  public Child(String kind, Data data) {
    this.kind = kind;
    this.data = data;
  }

  public String getKind() {
    return kind;
  }

  public Data getData() {
    return data;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Child child = (Child) o;

    if (data != null ? !data.equals(child.data) : child.data != null) return false;
    if (kind != null ? !kind.equals(child.kind) : child.kind != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = kind != null ? kind.hashCode() : 0;
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Child{" //
        + "kind='" + kind + '\'' //
        + ", data=" + data //
        + '}';
  }
}
