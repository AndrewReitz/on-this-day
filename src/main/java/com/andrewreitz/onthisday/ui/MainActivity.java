package com.andrewreitz.onthisday.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andrewreitz.onthisday.OnThisDayApp;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.SeenNavDrawer;
import com.andrewreitz.onthisday.mediaplayer.event.StopServiceEvent;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainView;
import com.andrewreitz.onthisday.ui.screen.ShowsScreen;
import com.andrewreitz.onthisday.ui.screen.StarredScreen;
import com.inkapplications.preferences.BooleanPreference;

import com.squareup.otto.Bus;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import flow.Flow;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static android.widget.Toast.LENGTH_LONG;
import static butterknife.ButterKnife.findById;
import static com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner.MenuAction;

public final class MainActivity extends Activity implements ActionBarOwner.View {

  @Inject AppContainer appContainer;
  @Inject @SeenNavDrawer BooleanPreference seenNavDrawer;
  @Inject ActionBarOwner actionBarOwner;
  @Inject Bus bus;

  @InjectView(R.id.container) MainView mainView;
  @InjectView(R.id.nav_drawer_layout) DrawerLayout drawerLayout;

  private boolean navDrawerEnable = true;
  private ActionBarDrawerToggle drawerToggle;
  private MortarActivityScope activityScope;
  private Flow mainFlow;
  private List<MenuAction> actionBarMenuAction;

  @OnClick(R.id.nav_drawer_home) void navigateHome() {
    mainFlow.replaceTo(new ShowsScreen());
    drawerLayout.closeDrawers();
  }

  @OnClick(R.id.nav_drawer_stared) void navigateToStarred() {
    mainFlow.replaceTo(new StarredScreen());
    drawerLayout.closeDrawers();
  }

  @OnClick(R.id.nav_drawer_settings) void navigateToSettings() {

  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OnThisDayApp app = OnThisDayApp.get(this);

    MortarScope parentScope = app.getRootScope();
    activityScope = Mortar.requireActivityScope(parentScope, new Main());
    Mortar.inject(this, this);
    activityScope.onCreate(savedInstanceState);

    ViewGroup container = appContainer.get(this, app);
    getLayoutInflater().inflate(R.layout.activity_main, container);

    actionBarOwner.takeView(this);

    ViewGroup leftDrawer = findById(this, R.id.navigation_drawer);
    LayoutInflater.from(this).inflate(R.layout.drawer_navigation, leftDrawer);
    ButterKnife.inject(this);

    setupNavigationDrawer();

    mainFlow = mainView.getFlow();
  }

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    activityScope.onSaveInstanceState(outState);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    if (item.getItemId() == android.R.id.home) {
      return mainFlow.goUp();
    }

    return super.onOptionsItemSelected(item);
  }

  /** Configure the action bar menu as required by {@link ActionBarOwner.View}. */
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (actionBarMenuAction != null) {
      for (MenuAction menuAction : actionBarMenuAction) {
        menu.add(menuAction.title)
            .setIcon(menuAction.resIcon)
            .setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS)
            .setOnMenuItemClickListener(menuItem -> {
              menuAction.action.call();
              return true;
            });
      }
    }
    return true;
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    if (isFinishing() && activityScope != null) {
      MortarScope parentScope = OnThisDayApp.get(this).getRootScope();
      parentScope.destroyChild(activityScope);
      activityScope = null;
    }

    bus.post(new StopServiceEvent());
  }

  /** Inform the view about back events. */
  @Override public void onBackPressed() {
    // Give the view a chance to handle going back. If it declines the honor, let super do its thing.
    if (!mainFlow.goBack()) super.onBackPressed();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override public Object getSystemService(@NonNull String name) {
    if (Mortar.isScopeSystemService(name)) {
      return activityScope;
    }
    return super.getSystemService(name);
  }

  @Override public void setShowHomeEnabled(boolean enabled) {
    ActionBar actionBar = getActionBar();
    //noinspection ConstantConditions
    actionBar.setDisplayShowHomeEnabled(enabled);
  }

  @Override public void setUpButtonEnabled(boolean enabled) {
    ActionBar actionBar = getActionBar();
    //noinspection ConstantConditions
    actionBar.setDisplayHomeAsUpEnabled(enabled);
    actionBar.setHomeButtonEnabled(enabled);
  }

  @Override public void setMenu(List<MenuAction> actions) {
    if (actions != actionBarMenuAction) {
      actionBarMenuAction = actions;
      invalidateOptionsMenu();
    }
  }

  @Override public void setNavDrawerEnabled(boolean enabled) {
    navDrawerEnable = enabled;
    if (drawerToggle != null) {
      drawerToggle.setDrawerIndicatorEnabled(enabled);
      drawerLayout.setDrawerLockMode(
          enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
  }

  @Override public Context getMortarContext() {
    return this;
  }

  private void setupNavigationDrawer() {
    final ActionBar actionBar = getActionBar();

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

      /** Called when a drawer has settled in a completely closed state. */
      public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        //noinspection ConstantConditions
        actionBar.setTitle(getString(R.string.app_name)); // TODO
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
      }

      /** Called when a drawer has settled in a completely open state. */
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        //noinspection ConstantConditions
        actionBar.setTitle(getString(R.string.app_name));
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
      }
    };

    drawerLayout.setDrawerListener(drawerToggle);
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

    drawerToggle.setDrawerIndicatorEnabled(navDrawerEnable);
    drawerLayout.setDrawerLockMode(
        navDrawerEnable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    // Display nav drawer if it's the first time the app is opened per Google's guidelines
    if (!seenNavDrawer.get()) {
      drawerLayout.postDelayed(() -> {
        drawerLayout.openDrawer(Gravity.START);
        Toast.makeText(MainActivity.this, R.string.drawer_intro_text, LENGTH_LONG).show();
      }, TimeUnit.MILLISECONDS.toMillis(500)); /* Half a second, but there's gotta be a better way*/
      seenNavDrawer.set(true);
    }
  }
}
