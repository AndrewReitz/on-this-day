package com.andrewreitz.onthisday.common;

import android.app.Application;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public final class OnThisDayStoreageManager {
  private static final String APPLICATION_STORAGE_DIRECTORY = "OnThisDay";

  private final Application application;

  @Inject OnThisDayStoreageManager(Application application) {
    this.application = checkNotNull(application, "Application may not be null.");
  }

  public File getSdCardDirectory() throws IOException {
    File externalStorageDirectory =
        new File(application.getFilesDir(), APPLICATION_STORAGE_DIRECTORY);

    if (!externalStorageDirectory.exists() && !externalStorageDirectory.mkdir()) {
      throw new IOException("Error creating sdcard directory.");
    }

    return externalStorageDirectory;
  }
}
