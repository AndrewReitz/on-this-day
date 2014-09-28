package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.model.Data;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShowItemView extends RelativeLayout {

  @InjectView(R.id.music_item_text) TextView textView;

  public ShowItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  public void bindTo(Data data) {
    textView.setText(data.getTitle());
  }
}