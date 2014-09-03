/* From https://github.com/JakeWharton/u2020 */
package com.andrewreitz.onthisday.ui;

import android.app.Activity;
import android.view.ViewGroup;

import com.andrewreitz.onthisday.OnThisDayApp;

import static butterknife.ButterKnife.findById;

/** An indirection which allows controlling the root container used for each activity. */
public interface AppContainer {
  /** The root {@link android.view.ViewGroup} into which the activity should place its contents. */
  ViewGroup get(Activity activity, OnThisDayApp app);

  /** An {@link AppContainer} which returns the normal activity content view. */
  AppContainer DEFAULT = new AppContainer() {
    @Override public ViewGroup get(Activity activity, OnThisDayApp app) {
      return findById(activity, android.R.id.content);
    }
  };
}
