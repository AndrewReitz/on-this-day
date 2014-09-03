package com.andrewreitz.onthisday;

import android.app.Application;

import javax.inject.Inject;

final class OnThisDayInitializer {
  private final Application application;

  @Inject OnThisDayInitializer(Application application) {
    this.application = application;
  }

  /** Init all things release here */
  void init() {
    /* Ex. Timber.plant(new CrashReportingTree()); */
  }
}

