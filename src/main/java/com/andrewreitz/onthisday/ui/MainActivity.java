package com.andrewreitz.onthisday.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andrewreitz.onthisday.OnThisDayApp;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.SeenNavDrawer;
import com.andrewreitz.onthisday.ui.misc.BaseActivity;
import com.inkapplications.preferences.BooleanPreference;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import flow.Backstack;
import flow.Flow;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.widget.Toast.LENGTH_LONG;
import static butterknife.ButterKnife.findById;

public final class MainActivity extends BaseActivity implements Flow.Listener {

  @Inject AppContainer appContainer;
  @Inject @SeenNavDrawer BooleanPreference seenNavDrawer;

  @InjectView(R.id.content_frame) View container;
  @InjectView(R.id.nav_drawer_layout) DrawerLayout drawerLayout;

  private ActionBarDrawerToggle drawerToggle;

  @OnClick(R.id.nav_drawer_home) void navigateHome() {

  }

  @OnClick(R.id.nav_drawer_stared) void navigateToStarred() {

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

    setupNavigationDrawer();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    // Handle your other action bar items...

    return super.onOptionsItemSelected(item);
  }

  @Override public void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {

  }

  private void setupNavigationDrawer() {
    final ActionBar actionBar = getActionBar();

    drawerToggle = new ActionBarDrawerToggle(
        this,
        drawerLayout,
        R.drawable.ic_drawer,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    ) {

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

    drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED, Gravity.END);

    //noinspection ConstantConditions
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);

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
