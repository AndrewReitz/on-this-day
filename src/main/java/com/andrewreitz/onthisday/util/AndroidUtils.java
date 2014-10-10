package com.andrewreitz.onthisday.util;

import android.os.Build;

public final class AndroidUtils {
  /**
   * Used to determine if the device is running Jelly Bean or greater
   *
   * @return True if the device is running Jelly Bean or greater, false
   * otherwise
   */
  public static boolean hasJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  private AndroidUtils() {
    throw new AssertionError();
  }
}
