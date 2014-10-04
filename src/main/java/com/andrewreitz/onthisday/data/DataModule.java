package com.andrewreitz.onthisday.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.andrewreitz.onthisday.data.api.archive.ArchiveService;
import com.andrewreitz.onthisday.data.api.reddit.OnThisDayRedditService;
import com.andrewreitz.onthisday.data.database.OnThisDaySalineOpenHelper;
import com.inkapplications.preferences.BooleanPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import shillelagh.Shillelagh;

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

  @Provides @Singleton OnThisDayRedditService provideRedditService(@Reddit RestAdapter restAdapter) {
    return restAdapter.create(OnThisDayRedditService.class);
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
    return new M3uWriter(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
  }

  @Provides @Singleton SQLiteOpenHelper provideSqliteOpenHelper(Application app) {
    return new OnThisDaySalineOpenHelper(app);
  }

  @Provides @Singleton Shillelagh provideShillelagh(SQLiteOpenHelper sqLiteOpenHelper) {
    return new Shillelagh(sqLiteOpenHelper);
  }
}
