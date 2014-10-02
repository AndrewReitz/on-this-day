/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.andrewreitz.onthisday.ui.motar.android;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;
import rx.functions.Action0;

/** Allows shared configuration of the Android ActionBar. */
public class ActionBarOwner extends Presenter<ActionBarOwner.View> {
  public interface View {
    void setShowHomeEnabled(boolean enabled);

    void setUpButtonEnabled(boolean enabled);

    void setTitle(CharSequence title);

    void setMenu(List<MenuAction> actions);

    void setNavDrawerEnabled(boolean enabled);

    Context getMortarContext();
  }

  public static class Config {
    public final boolean showHomeEnabled;
    public final boolean upButtonEnabled;
    public final boolean navDrawerEnabled;
    public final CharSequence title;
    public final List<MenuAction> actions;

    public Config(boolean showHomeEnabled, boolean upButtonEnabled, boolean navDrawerEnabled,
        CharSequence title, MenuAction... actions) {
      this.showHomeEnabled = showHomeEnabled;
      this.upButtonEnabled = upButtonEnabled;
      this.navDrawerEnabled = navDrawerEnabled;
      this.title = title;
      this.actions = actions == null ? Collections.emptyList() : Arrays.asList(actions);
    }

    public Config withAction(MenuAction... actions) {
      return new Config(showHomeEnabled, upButtonEnabled, navDrawerEnabled, title, actions);
    }
  }

  public static class MenuAction {
    @DrawableRes public final int resIcon;

    public final CharSequence title;
    public final Action0 action;

    public MenuAction(@DrawableRes int resIcon, CharSequence title, Action0 action) {
      this.resIcon = resIcon;
      this.title = title;
      this.action = action;
    }
  }

  private Config config;

  ActionBarOwner() {
  }

  @Override public void onLoad(Bundle savedInstanceState) {
    if (config != null) update();
  }

  public void setConfig(Config config) {
    this.config = config;
    update();
  }

  public Config getConfig() {
    return config;
  }

  @Override protected MortarScope extractScope(View view) {
    return Mortar.getScope(view.getMortarContext());
  }

  private void update() {
    View view = getView();
    if (view == null) return;

    view.setShowHomeEnabled(config.showHomeEnabled);
    view.setUpButtonEnabled(config.upButtonEnabled);
    view.setNavDrawerEnabled(config.navDrawerEnabled);
    view.setTitle(config.title);
    view.setMenu(config.actions);
  }
}
