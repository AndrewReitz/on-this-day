package com.andrewreitz.onthisday.data.api.reddit.model;

import java.util.Collections;
import java.util.List;

public final class Listing {
  private final String modhash;
  private final List<Child> children;
  private final String after;
  private final String before;

  public Listing(String modhash, List<Child> children, String after, String before) {
    this.modhash = modhash;
    this.children = children;
    this.after = after;
    this.before = before;
  }

  public String getModhash() {
    return modhash;
  }

  public List<Child> getChildren() {
    return Collections.unmodifiableList(children);
  }

  public String getAfter() {
    return after;
  }

  public String getBefore() {
    return before;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Listing listing = (Listing) o;

    if (after != null ? !after.equals(listing.after) : listing.after != null) return false;
    if (before != null ? !before.equals(listing.before) : listing.before != null) return false;
    if (children != null ? !children.equals(listing.children) : listing.children != null) {
      return false;
    }
    if (modhash != null ? !modhash.equals(listing.modhash) : listing.modhash != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = modhash != null ? modhash.hashCode() : 0;
    result = 31 * result + (children != null ? children.hashCode() : 0);
    result = 31 * result + (after != null ? after.hashCode() : 0);
    result = 31 * result + (before != null ? before.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Listing{" //
        + "modhash='" + modhash + '\'' //
        + ", children=" + children //
        + ", after='" + after + '\'' //
        + ", before='" + before + '\'' //
        + '}';
  }
}
