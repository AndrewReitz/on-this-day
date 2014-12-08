package com.andrewreitz.onthisday;

import android.app.Application;

import com.andrewreitz.onthisday.data.DataModule;
import com.andrewreitz.onthisday.mediaplayer.SimpleMusicPlayerModule;
import com.andrewreitz.onthisday.ui.UiModule;
import com.andrewreitz.onthisday.ui.flow.GsonParcer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.otto.Bus;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Parcer;

@Module(
    includes = {
        UiModule.class,
        DataModule.class,
        SimpleMusicPlayerModule.class
    },
    injects = {
        OnThisDayApp.class
    },
    library = true
)
public final class OnThisDayModule {
  private final OnThisDayApp app;

  public OnThisDayModule(OnThisDayApp app) {
    this.app = app;
  }

  @Provides @Singleton Application provideApplication() {
    return app;
  }

  @Provides @Singleton Parcer<Object> provideParcer(Gson gson) {
    return new GsonParcer<>(gson);
  }

  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides @Singleton Bus provideBus() {
    return new Bus();
  }
}
