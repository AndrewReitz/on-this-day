package com.andrewreitz.onthisday;

import android.app.Application;

import android.os.StrictMode;
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

    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
        .detectDiskReads() //
        .detectAll() //
        .penaltyLog() //
        .penaltyDialog() //
        .build());

    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder() //
        .detectAll() //
        .penaltyLog() //
        .build());
  }
}
