package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.util.TextFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Typeface.BOLD;

public final class ShowItemView extends FrameLayout {

  private static final Pattern PATTERN_TO_BOLD =
      Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}");

  @InjectView(R.id.music_item_text) TextView textView;

  public ShowItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  public void bindTo(Data show) {
    final String title = show.getTitle().replace("On this day: ", "");
    final Matcher titleMatcher = PATTERN_TO_BOLD.matcher(title);
    //noinspection ResultOfMethodCallIgnored
    titleMatcher.find();
    final CharSequence formattedTitle = TextFormatter.setSpan(title, titleMatcher.start(), //
        titleMatcher.end(), new StyleSpan(BOLD));
    textView.setText(formattedTitle);
  }
}
