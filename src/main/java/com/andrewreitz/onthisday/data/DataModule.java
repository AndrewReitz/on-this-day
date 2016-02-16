package com.andrewreitz.onthisday.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.andrewreitz.onthisday.data.api.archive.ArchiveService;
import com.andrewreitz.onthisday.data.api.reddit.OnThisDayRedditService;
import com.andrewreitz.onthisday.data.database.OnThisDaySalineOpenHelper;
import com.inkapplications.preferences.BooleanPreference;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.RestAdapter;
import shillelagh.Shillelagh;
import timber.log.Timber;

@Module(
    complete = false,
    library = true)
public final class DataModule {
  @Provides @Singleton SharedPreferences provideSharedPreferences(Application app) {
    return PreferenceManager.getDefaultSharedPreferences(app);
  }

  @Provides @Singleton @SeenNavDrawer BooleanPreference provideSeenNavDrawer(
      SharedPreferences sharedPreferences) {
    return new BooleanPreference(sharedPreferences, "seen_nav_drawer", false);
  }

  @Provides @Singleton Picasso providePicasso(Application app) {
    return new Picasso.Builder(app) //
        .listener(new Picasso.Listener() {
          @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
            Timber.e(e, "Failed to load image: %s", uri);
          }
        }).build();
  }

  @Provides @Singleton @Reddit RestAdapter provideRedditRestAdapter() {
    return new RestAdapter.Builder() //
        .setLogLevel(RestAdapter.LogLevel.BASIC) // TODO
        .setEndpoint("http://reddit.com") //
        .build();
  }

  @Provides @Singleton OnThisDayRedditService provideRedditService(
      @Reddit RestAdapter restAdapter) {
    return restAdapter.create(OnThisDayRedditService.class);
  }

  @Provides @Singleton @Archive RestAdapter provideArchiveRestAdapter() {
    return new RestAdapter.Builder() //
        .setLogLevel(RestAdapter.LogLevel.BASIC) // TODO
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
