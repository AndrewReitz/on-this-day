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

  /**
   * Used to determine if the device is running
   * Jelly Bean MR2 (Android 4.3) or greater
   *
   * @return True if the device is running Jelly Bean MR2 or greater,
   * false otherwise
   */
  public static boolean hasJellyBeanMR2() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
  }

  private AndroidUtils() {
    throw new AssertionError();
  }
}
