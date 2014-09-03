package com.andrewreitz.onthisday;

import android.app.Application;
import android.content.Context;

import com.andrewreitz.onthisday.ui.ActivityHierarchyServer;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import hugo.weaving.DebugLog;
import timber.log.Timber;

public class OnThisDayApp extends Application {

  @Inject ActivityHierarchyServer activityHierarchyServer;
  @Inject OnThisDayInitializer initializer;

  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    buildObjectGraphAndInject();
    registerActivityLifecycleCallbacks(activityHierarchyServer);
    initializer.init();
  }

  @DebugLog
  public void buildObjectGraphAndInject() {
    objectGraph = ObjectGraph.create(Modules.list(this));
    objectGraph.inject(this);
  }

  public ObjectGraph getObjectGraph() {
    return objectGraph;
  }

  public void inject(Object o) {
    objectGraph.inject(o);
  }

  public static OnThisDayApp get(Context context) {
    return (OnThisDayApp) context.getApplicationContext();
  }

}
