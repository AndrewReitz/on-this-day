package com.andrewreitz.onthisday.ui.musiclist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.RedditRepository;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.ui.misc.BetterViewAnimator;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.Mortar;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MusicListView extends BetterViewAnimator {
  @Inject RedditRepository redditRepository;

  @InjectView(R.id.music_list) ListView musicListView;

  private Subscription request;

  private final MusicListAdapter adapter;

  public MusicListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);

    adapter = new MusicListAdapter(context);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);

    musicListView.setAdapter(adapter);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    request = redditRepository.loadReddit()
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(data -> {
          adapter.updateData(data);
          setDisplayedChildId(R.id.music_list);
        });
  }

  @Override protected void onDetachedFromWindow() {
    request.unsubscribe();
    super.onDetachedFromWindow();
  }
}
