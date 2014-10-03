package com.andrewreitz.onthisday.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andrewreitz.onthisday.data.api.archive.ArchiveService;
import com.andrewreitz.onthisday.data.api.reddit.RedditService;
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

  @Provides @Singleton @Reddit RestAdapter provideRedditRestAdapter() {
    return new RestAdapter.Builder() //
        .setLogLevel(RestAdapter.LogLevel.FULL) // TOOD
        .setEndpoint("http://reddit.com") //
        .build();
  }

  @Provides @Singleton RedditService provideRedditService(@Reddit RestAdapter restAdapter) {
    return restAdapter.create(RedditService.class);
  }

  @Provides @Singleton @Archive RestAdapter provideArchiveRestAdapter() {
    return new RestAdapter.Builder() //
        .setLogLevel(RestAdapter.LogLevel.FULL) // TOOD
        .setEndpoint("https://archive.org") //
        .build();
  }

  @Provides @Singleton ArchiveService provideArchiveService(@Archive RestAdapter restAdapter) {
    return restAdapter.create(ArchiveService.class);
  }

  @Provides @Singleton ArchiveRepository provideArchiveRepository(ArchiveService archiveService) {
    return new ArchiveRepository(archiveService);
  }

  @Provides @Singleton M3uWriter provideM3uWriter(Application app) {
    return new M3uWriter(app);
  }
}
