package com.andrewreitz.onthisday.ui.musiclist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.ui.misc.BindableView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MusicItemView extends RelativeLayout implements BindableView<Data> {

  @InjectView(R.id.music_item_text) TextView textView;

  public MusicItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  @Override public void bindTo(Data data) {
    textView.setText(data.getTitle());
  }
}
