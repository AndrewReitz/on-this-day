package com.andrewreitz.onthisday.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andrewreitz.onthisday.data.api.RedditService;
import com.inkapplications.preferences.BooleanPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
    complete = false,
    library = true
)
public final class DataModule {
  @Provides @Singleton SharedPreferences provideSharedPreferences(Application app) {
    return PreferenceManager.getDefaultSharedPreferences(app);
  }

  @Provides @Singleton @SeenNavDrawer BooleanPreference provideSeenNavDrawer(
      SharedPreferences sharedPreferences) {
    return new BooleanPreference(sharedPreferences, "seen_nav_drawer", false);
  }

  @Provides @Singleton RestAdapter provideRestAdapter() {
    return new RestAdapter.Builder()
        .setLogLevel(RestAdapter.LogLevel.FULL) // todo
        .setEndpoint("http://reddit.com")
        .build();
  }

  @Provides @Singleton RedditService provideRedditService(RestAdapter restAdapter) {
    return restAdapter.create(RedditService.class);
  }
}
