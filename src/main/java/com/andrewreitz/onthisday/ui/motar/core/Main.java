package com.andrewreitz.onthisday.ui.motar.core;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.ui.MainActivity;
import com.andrewreitz.onthisday.ui.UiModule;
import com.andrewreitz.onthisday.ui.flow.IsMain;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarModule;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.util.FlowOwner;
import com.andrewreitz.onthisday.ui.screen.ShowsScreen;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Parcer;
import mortar.Blueprint;
import rx.functions.Action0;

public class Main implements Blueprint {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      includes = ActionBarModule.class,
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

  @Singleton public static class Presenter extends FlowOwner<Blueprint, MainView> {
    private final Context context;
    private final ActionBarOwner actionBarOwner;

    @Inject Presenter(Application context, Parcer<Object> flowParcer,
        ActionBarOwner actionBarOwner) {
      super(flowParcer);
      this.context = context;
      this.actionBarOwner = actionBarOwner;
    }

    @Override public void showScreen(Blueprint newScreen, Flow.Direction direction) {
      //if (newScreen instanceof IsMain) {
      //  actionBarOwner.setConfig(
      //      new ActionBarOwner.Config(true, true, true, context.getString(R.string.app_name),
      //          null));
      //} else {
      //  boolean hasUp = newScreen instanceof HasParent;
      //  actionBarOwner.setConfig(
      //      new ActionBarOwner.Config(true, hasUp, false, context.getString(R.string.app_name),
      //          new ActionBarOwner.MenuAction(R.drawable.ic_favorite,
      //              context.getString(R.string.favorite_show),
      //              () -> Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()),
      //          new ActionBarOwner.MenuAction(R.drawable.ic_play,
      //              context.getString(R.string.play_show) ,
      //              () -> Toast.makeText(context, "Test", Toast.LENGTH_LONG).show())));
      //}

      super.showScreen(newScreen, direction);
    }

    @Override protected Blueprint getFirstScreen() {
      return new ShowsScreen();
    }
  }
}
