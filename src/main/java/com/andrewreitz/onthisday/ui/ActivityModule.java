package com.andrewreitz.onthisday.ui;

import android.app.Activity;

import com.andrewreitz.onthisday.OnThisDayModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        MainActivity.class
    },
    addsTo = OnThisDayModule.class,
    library = true
)
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides @Singleton Activity provideActivityContext() {
    return activity;
  }
}
