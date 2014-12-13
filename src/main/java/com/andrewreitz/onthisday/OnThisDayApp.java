package com.andrewreitz.onthisday;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.andrewreitz.onthisday.ui.ActivityHierarchyServer;
import com.andrewreitz.onthisday.ui.show.ShowModule;
import dagger.ObjectGraph;
import hugo.weaving.DebugLog;
import java.util.Collections;
import java.util.Map;
import javax.inject.Inject;
import prism.framework.GraphContext;
import prism.framework.KernelContext;
import prism.framework.LifecycleSubscriber;
import prism.framework.PrismKernel;

public final class OnThisDayApp extends Application implements KernelContext, GraphContext {

  @Inject ActivityHierarchyServer activityHierarchyServer;
  @Inject OnThisDayInitializer initializer;

  private ObjectGraph objectGraph;
  private PrismKernel kernel;

  @Override
  public void onCreate() {
    super.onCreate();

    buildObjectGraphAndInject();

    initializer.init();
    kernel = new PrismKernel(this);

    registerActivityLifecycleCallbacks(activityHierarchyServer);
    registerActivityLifecycleCallbacks(new LifecycleSubscriber(this));
  }

  @DebugLog
  public void buildObjectGraphAndInject() {
    objectGraph = ObjectGraph.create(Modules.list(this));
    objectGraph.inject(this);
  }

  public static OnThisDayApp get(Context context) {
    return (OnThisDayApp) context.getApplicationContext();
  }

  @Override public PrismKernel getKernel() {
    return kernel;
  }

  @Override public Object[] getActivityModules(Activity activity) {
    return new Object[] {
        ShowModule.class,
    };
  }

  @Override public ObjectGraph getApplicationGraph() {
    return objectGraph;
  }

  @Override public Map<Class, Object> getScopeModules(Activity activity) {
    return Collections.emptyMap();
  }
}
