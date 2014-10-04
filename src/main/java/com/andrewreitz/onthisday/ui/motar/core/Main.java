package com.andrewreitz.onthisday.ui.motar.core;

import com.andrewreitz.onthisday.ui.MainActivity;
import com.andrewreitz.onthisday.ui.UiModule;
import com.andrewreitz.onthisday.ui.motar.android.AndroidModule;
import com.andrewreitz.onthisday.ui.motar.util.FlowOwner;
import com.andrewreitz.onthisday.ui.screen.ShowsScreen;
import dagger.Provides;
import flow.Flow;
import flow.Parcer;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.Blueprint;

public class Main implements Blueprint {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      includes = AndroidModule.class,
      injects = { MainView.class, MainActivity.class },
      addsTo = UiModule.class,
      complete = false,
      library = true
  )
  public static class Module {
    @Provides @MainScope Flow provideFlow(Presenter presenter) {
      return presenter.getFlow();
    }
  }

  @Singleton
  public static class Presenter extends FlowOwner<Blueprint, MainView> {

    @Inject Presenter(Parcer<Object> flowParcer) {
      super(flowParcer);
    }

    @Override protected Blueprint getFirstScreen() {
      return new ShowsScreen();
    }
  }
}
