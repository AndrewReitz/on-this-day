package com.andrewreitz.onthisday.ui.motar;

import android.os.Bundle;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;

@Layout(R.layout.test_view)
public class TestScreen implements Blueprint {
  @Override public String getMortarScopeName() {
    return getClass().getName();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @dagger.Module(
      injects = TestView.class,
      addsTo = Main.Module.class,
      complete = false
  )
  static class Module {
  }

  @Singleton
  public static class Presenter extends ViewPresenter<TestView> {
    private final Flow flow;

    @Inject Presenter(@MainScope Flow flow) {
      this.flow = flow;
    }

    @Override public void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
    }
  }
}
