package com.andrewreitz.onthisday.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.andrewreitz.onthisday.R;
import hugo.weaving.DebugLog;

/**
 * Button that shows a down arrow, then when pressed shows the up arrow.
 */
@DebugLog
public final class MoreLessButton extends ImageView implements View.OnClickListener {
  private static final MoreLessListener EMPTY_ONCLICK_LISTENER = new MoreLessListener() {
    @Override public void onMore() { }
    @Override public void onLess() { }
  };

  private boolean more = false;
  private MoreLessListener moreLessListener = EMPTY_ONCLICK_LISTENER;

  public MoreLessButton(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.setImageResource(R.drawable.ic_expand_more);
    this.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    more = !more;

    if (more) {
      this.moreLessListener.onMore();
      this.setImageResource(R.drawable.ic_expand_less);
    } else {
      this.moreLessListener.onLess();
      this.setImageResource(R.drawable.ic_expand_more);
    }
  }

  public void setMoreLessListener(MoreLessListener moreLessListener) {
    this.moreLessListener = moreLessListener == null ? EMPTY_ONCLICK_LISTENER : moreLessListener;
  }

  /** Listener for receiving more and less events. */
  public interface MoreLessListener {
    void onMore();
    void onLess();
  }
}
