package com.andrewreitz.onthisday.ui.motar.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.andrewreitz.onthisday.ui.motar.util.CanShowScreen;
import com.andrewreitz.onthisday.ui.motar.util.ScreenConductor;

import javax.inject.Inject;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;

public class MainView extends FrameLayout implements CanShowScreen<Blueprint> {
  @Inject Main.Presenter presenter;

  private final ScreenConductor<Blueprint> screenMaestro;

  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);
    screenMaestro = new ScreenConductor<>(context, this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    presenter.takeView(this);
  }

  public Flow getFlow() {
    return presenter.getFlow();
  }


  @Override public void showScreen(Blueprint screen, Flow.Direction direction) {
    screenMaestro.showScreen(screen, direction);
  }
}
