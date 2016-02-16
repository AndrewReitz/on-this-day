package com.andrewreitz.onthisday.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.andrewreitz.onthisday.OnThisDayApp;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.SeenNavDrawer;
import com.andrewreitz.onthisday.ui.eventbus.ToolBarTitleEvent;
import com.andrewreitz.onthisday.ui.show.ShowListFragment;
import com.inkapplications.preferences.BooleanPreference;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import hugo.weaving.DebugLog;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import static android.widget.Toast.LENGTH_LONG;
import static butterknife.ButterKnife.findById;

@DebugLog
public final class MainActivity extends ActionBarActivity {
  private static final ShowListFragment showListFragment = new ShowListFragment();

  @Inject AppContainer appContainer;
  @Inject @SeenNavDrawer BooleanPreference seenNavDrawer;
  @Inject Bus bus;

  @InjectView(R.id.container) View mainView;
  @InjectView(R.id.nav_drawer_layout) DrawerLayout drawerLayout;
  @InjectView(R.id.toolbar) Toolbar toolbar;

  private ActionBarDrawerToggle drawerToggle;

  @OnClick(R.id.nav_drawer_home) void navigateHome() {
    //mainFlow.replaceTo(new ShowsScreen());
    drawerLayout.closeDrawers();
  }

  @OnClick(R.id.nav_drawer_stared) void navigateToStarred() {
    //mainFlow.replaceTo(new StarredScreen());
    drawerLayout.closeDrawers();
  }

  @OnClick(R.id.nav_drawer_settings) void navigateToSettings() {

  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OnThisDayApp app = OnThisDayApp.get(this);

    ViewGroup container = appContainer.get(this, app);
    getLayoutInflater().inflate(R.layout.activity_main, container);

    ViewGroup leftDrawer = findById(this, R.id.navigation_drawer);
    LayoutInflater.from(this).inflate(R.layout.drawer_navigation, leftDrawer);
    ButterKnife.inject(this);

    setSupportActionBar(toolbar);
    setupNavigationDrawer();

    getFragmentManager().beginTransaction()
        .add(R.id.container, showListFragment, "show_list_fragment")
        .commit();
  }

  @Override protected void onResume() {
    super.onResume();
    bus.register(this);
  }

  @Override protected void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    return true;
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

  @Subscribe
  public void onTitleBarEvent(ToolBarTitleEvent event) {
    toolbar.setTitle(event.title);
  }

  private void setupNavigationDrawer() {
    final ActionBar actionBar = getSupportActionBar();

    drawerToggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close) {

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

    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    // Display nav drawer if it's the first time the app is opened per Google's guidelines
    if (!seenNavDrawer.get()) {
      drawerLayout.postDelayed(new Runnable() {
        @Override public void run() {
          drawerLayout.openDrawer(Gravity.START);
          Toast.makeText(MainActivity.this, R.string.drawer_intro_text, LENGTH_LONG).show();
        }
      }, TimeUnit.MILLISECONDS.toMillis(500)); /* Half a second */
      seenNavDrawer.set(true);
    }
  }
}
