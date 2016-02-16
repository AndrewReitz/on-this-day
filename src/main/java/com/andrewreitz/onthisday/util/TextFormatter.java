package com.andrewreitz.onthisday.util;

import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;

public final class TextFormatter {
  /**
   * Adds style to strings for TextViews by using the SpannableStringBuilder.
   *
   * @param text The text to add style to.
   * @param start The index of the text to begin formatting at.
   * @param end The index to stop formatting at.
   * @param cs The Style to be applied.
   * @return The formatted text.
   */
  public static CharSequence setSpan(CharSequence text, int start, int end, CharacterStyle cs) {
    if (start < 0) throw new IllegalArgumentException("Start can not be less than 0.");
    if (end < 1) throw new IllegalArgumentException("End can not be less than 1.");

    // Copy the spannable string to a mutable spannable string
    SpannableStringBuilder ssb = new SpannableStringBuilder(text);
    ssb.setSpan(cs, start, end, 0);

    return ssb;
  }

  private TextFormatter() {
    throw new UnsupportedOperationException("No Instances");
  }
}
