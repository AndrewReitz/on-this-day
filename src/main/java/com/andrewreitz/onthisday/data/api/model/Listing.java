
package com.andrewreitz.onthisday.data.api.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public final class Listing {
  private final String modhash;
  private final List<Child> children;
  private final String after;
  private final String before;

  private Listing(String modhash, List<Child> children, String after, String before) {
    this.modhash = modhash;
    this.children = children;
    this.after = after;
    this.before = before;
  }

  public String getModhash() {
    return modhash;
  }

  public List<Child> getChildren() {
    return children;
  }

  public String getAfter() {
    return after;
  }

  public String getBefore() {
    return before;
  }
}
