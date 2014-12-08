package com.andrewreitz.onthisday;

import android.app.Application;
import android.content.Context;
import com.andrewreitz.onthisday.ui.ActivityHierarchyServer;
import dagger.ObjectGraph;
import hugo.weaving.DebugLog;
import javax.inject.Inject;
import mortar.Mortar;
import mortar.MortarScope;

public class OnThisDayApp extends Application {

  @Inject ActivityHierarchyServer activityHierarchyServer;
  @Inject OnThisDayInitializer initializer;

  private ObjectGraph objectGraph;
  private MortarScope rootScope;

  @Override
  public void onCreate() {
    super.onCreate();
    buildObjectGraphAndInject();
    registerActivityLifecycleCallbacks(activityHierarchyServer);
    initializer.init();

    rootScope = Mortar.createRootScope(BuildConfig.DEBUG, objectGraph);
  }

  @DebugLog
  public void buildObjectGraphAndInject() {
    objectGraph = ObjectGraph.create(Modules.list(this));
    objectGraph.inject(this);
  }

  public MortarScope getRootScope() {
    return rootScope;
  }

  public static OnThisDayApp get(Context context) {
    return (OnThisDayApp) context.getApplicationContext();
  }
}
