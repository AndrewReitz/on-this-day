package com.andrewreitz.onthisday;

import android.app.Application;

import com.andrewreitz.onthisday.data.DataModule;
import com.andrewreitz.onthisday.ui.UiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    includes = {
        UiModule.class,
        DataModule.class
    },
    injects = {
        OnThisDayApp.class
    }
)
public final class OnThisDayModule {
  private final OnThisDayApp app;

  public OnThisDayModule(OnThisDayApp app) {
    this.app = app;
  }

  @Provides @Singleton Application provideApplication() {
    return app;
  }
}
