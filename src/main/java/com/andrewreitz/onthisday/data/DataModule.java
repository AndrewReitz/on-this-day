package com.andrewreitz.onthisday.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.inkapplications.preferences.BooleanPreference;
import com.inkapplications.preferences.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
