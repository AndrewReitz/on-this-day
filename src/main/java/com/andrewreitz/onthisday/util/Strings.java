package com.andrewreitz.onthisday.util;

import java.util.Iterator;
import java.util.List;

/** From https://github.com/JakeWharton/u2020 */
public final class Strings {
  private Strings() {
    // No instances.
  }

  /** Checks if the string is blank, like TextUtils.isEmpty() but better */
  public static boolean isBlank(CharSequence string) {
    return (string == null || string.toString().trim().length() == 0);
  }

  /** Checks if a string there, if not returns the default string */
  public static String valueOrDefault(String string, String defaultString) {
    return isBlank(string) ? defaultString : string;
  }

  /** Truncates the string at the length specified */
  public static String truncateAt(String string, int length) {
    return string.length() > length ? string.substring(0, length) : string;
  }

  /** Capitalizes the first letter of the string passed in */
  public static String capitalize(String string) {
    if (isBlank(string)) {
      return "";
    }
    char first = string.charAt(0);
    if (Character.isUpperCase(first)) {
      return string;
    } else {
      return Character.toUpperCase(first) + string.substring(1);
    }
  }

  public static <T> String join(List<T> list) {
    StringBuilder sb = new StringBuilder();
    final Iterator<T> iterator = list.iterator();

    while (iterator.hasNext()) {
      sb.append(iterator.next().toString());
      if (iterator.hasNext()) {
        sb.append(", ");
      }
    }

    return sb.toString();
  }
}
