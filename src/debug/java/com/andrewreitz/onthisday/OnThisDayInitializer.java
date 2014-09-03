package com.andrewreitz.onthisday;

import android.app.Application;

import javax.inject.Inject;

import timber.log.Timber;

final class OnThisDayInitializer {
  private Application application;

  @Inject OnThisDayInitializer(Application application) {
    this.application = application;
  }

  /** Init all things debug here */
  void init() {
    Timber.plant(new Timber.DebugTree());
  }
}
