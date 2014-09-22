package com.andrewreitz.onthisday.ui.musiclist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.andrewreitz.onthisday.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MusicListView extends FrameLayout {
  @InjectView(R.id.music_list) ListView musicListView;

  public MusicListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }
}
