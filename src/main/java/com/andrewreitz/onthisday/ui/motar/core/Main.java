package com.andrewreitz.onthisday.ui.motar.core;

import com.andrewreitz.onthisday.ui.MainActivity;
import com.andrewreitz.onthisday.ui.UiModule;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarModule;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.util.FlowOwner;
import com.andrewreitz.onthisday.ui.musiclist.MusicList;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Parcer;
import mortar.Blueprint;

public class Main implements Blueprint {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      includes = ActionBarModule.class,
      injects = {MainView.class, MainActivity.class},
      addsTo = UiModule.class,
      complete = false,
      library = true
  )
  public static class Module {
    @Provides @MainScope Flow provideFlow(Presenter presenter) {
      return presenter.getFlow();
    }
  }

  @Singleton public static class Presenter extends FlowOwner<Blueprint, MainView> {
    private final ActionBarOwner actionBarOwner;

    @Inject Presenter(Parcer<Object> flowParcer, ActionBarOwner actionBarOwner) {
      super(flowParcer);
      this.actionBarOwner = actionBarOwner;
    }

    @Override public void showScreen(Blueprint newScreen, Flow.Direction direction) {
      if (newScreen instanceof IsMain) {
        actionBarOwner.setConfig(new ActionBarOwner.Config(false, true, "On This day", null));
      } else {
        boolean hasUp = newScreen instanceof HasParent;
        String title = newScreen.getClass().getSimpleName();
        //ActionBarOwner.MenuAction menu = hasUp ? null : new ActionBarOwner.MenuAction("Friends", () -> { });
        actionBarOwner.setConfig(new ActionBarOwner.Config(false, hasUp, title, null));
      }

      super.showScreen(newScreen, direction);
    }

    @Override protected Blueprint getFirstScreen() {
      return new MusicList();
    }
  }
}
