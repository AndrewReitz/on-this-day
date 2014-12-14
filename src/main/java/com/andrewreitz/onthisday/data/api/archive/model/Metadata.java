package com.andrewreitz.onthisday.data.api.archive.model;

import java.io.Serializable;
import java.util.Collections;
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
    return Collections.unmodifiableList(title);
  }

  public List<String> getCreator() {
    return Collections.unmodifiableList(creator);
  }

  public List<String> getDescription() {
    return Collections.unmodifiableList(description);
  }

  public List<String> getDate() {
    return Collections.unmodifiableList(date);
  }

  public List<String> getYear() {
    return Collections.unmodifiableList(year);
  }

  public List<String> getSubject() {
    return Collections.unmodifiableList(subject);
  }

  public List<String> getVenue() {
    return Collections.unmodifiableList(venue);
  }

  public List<String> getCoverage() {
    return Collections.unmodifiableList(coverage);
  }

  public List<String> getTransferer() {
    return Collections.unmodifiableList(transferer);
  }

  public List<String> getRuntime() {
    return Collections.unmodifiableList(runtime);
  }

  public List<String> getNotes() {
    return Collections.unmodifiableList(notes);
  }

  public List<String> getUpdatedate() {
    return Collections.unmodifiableList(updatedate);
  }

  public List<String> getUpdater() {
    return Collections.unmodifiableList(updater);
  }

  public List<String> getPublicdate() {
    return Collections.unmodifiableList(publicdate);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Metadata metadata = (Metadata) o;

    if (coverage != null ? !coverage.equals(metadata.coverage) : metadata.coverage != null) {
      return false;
    }
    if (creator != null ? !creator.equals(metadata.creator) : metadata.creator != null) {
      return false;
    }
    if (date != null ? !date.equals(metadata.date) : metadata.date != null) return false;
    if (description != null ? !description.equals(metadata.description)
        : metadata.description != null) {
      return false;
    }
    if (notes != null ? !notes.equals(metadata.notes) : metadata.notes != null) return false;
    if (publicdate != null ? !publicdate.equals(metadata.publicdate)
        : metadata.publicdate != null) {
      return false;
    }
    if (runtime != null ? !runtime.equals(metadata.runtime) : metadata.runtime != null) {
      return false;
    }
    if (subject != null ? !subject.equals(metadata.subject) : metadata.subject != null) {
      return false;
    }
    if (title != null ? !title.equals(metadata.title) : metadata.title != null) return false;
    if (transferer != null ? !transferer.equals(metadata.transferer)
        : metadata.transferer != null) {
      return false;
    }
    if (updatedate != null ? !updatedate.equals(metadata.updatedate)
        : metadata.updatedate != null) {
      return false;
    }
    if (updater != null ? !updater.equals(metadata.updater) : metadata.updater != null) {
      return false;
    }
    if (venue != null ? !venue.equals(metadata.venue) : metadata.venue != null) return false;
    if (year != null ? !year.equals(metadata.year) : metadata.year != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (creator != null ? creator.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (year != null ? year.hashCode() : 0);
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (venue != null ? venue.hashCode() : 0);
    result = 31 * result + (coverage != null ? coverage.hashCode() : 0);
    result = 31 * result + (transferer != null ? transferer.hashCode() : 0);
    result = 31 * result + (runtime != null ? runtime.hashCode() : 0);
    result = 31 * result + (notes != null ? notes.hashCode() : 0);
    result = 31 * result + (updatedate != null ? updatedate.hashCode() : 0);
    result = 31 * result + (updater != null ? updater.hashCode() : 0);
    result = 31 * result + (publicdate != null ? publicdate.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Metadata{" //
        + "title=" + title //
        + ", creator=" + creator //
        + ", description=" + description //
        + ", date=" + date //
        + ", year=" + year //
        + ", subject=" + subject //
        + ", venue=" + venue //
        + ", coverage=" + coverage //
        + ", transferer=" + transferer //
        + ", runtime=" + runtime //
        + ", notes=" + notes //
        + ", updatedate=" + updatedate //
        + ", updater=" + updater //
        + ", publicdate=" + publicdate //
        + '}';
  }
}
