package com.andrewreitz.onthisday.data.api.archive.model;

import java.io.Serializable;
import java.util.List;

public class Metadata implements Serializable {
  private final List<String> title;
  private final List<String> creator;
  private final List<String> description;
  private final List<String> date;
  private final List<String> year;
  private final List<String> subject;
  private final List<String> venue;
  private final List<String> coverage;
  private final List<String> transferer;
  private final List<String> runtime;
  private final List<String> notes;
  private final List<String> updatedate;
  private final List<String> updater;
  private final List<String> publicdate;

  public Metadata(List<String> title, List<String> creator, List<String> description,
      List<String> date, List<String> year, List<String> subject, List<String> venue,
      List<String> coverage, List<String> transferer, List<String> runtime, List<String> notes,
      List<String> updatedate, List<String> updater, List<String> publicdate) {
    this.title = title;
    this.creator = creator;
    this.description = description;
    this.date = date;
    this.year = year;
    this.subject = subject;
    this.venue = venue;
    this.coverage = coverage;
    this.transferer = transferer;
    this.runtime = runtime;
    this.notes = notes;
    this.updatedate = updatedate;
    this.updater = updater;
    this.publicdate = publicdate;
  }

  public List<String> getTitle() {
    return title;
  }

  public List<String> getCreator() {
    return creator;
  }

  public List<String> getDescription() {
    return description;
  }

  public List<String> getDate() {
    return date;
  }

  public List<String> getYear() {
    return year;
  }

  public List<String> getSubject() {
    return subject;
  }

  public List<String> getVenue() {
    return venue;
  }

  public List<String> getCoverage() {
    return coverage;
  }

  public List<String> getTransferer() {
    return transferer;
  }

  public List<String> getRuntime() {
    return runtime;
  }

  public List<String> getNotes() {
    return notes;
  }

  public List<String> getUpdatedate() {
    return updatedate;
  }

  public List<String> getUpdater() {
    return updater;
  }

  public List<String> getPublicdate() {
    return publicdate;
  }
}
