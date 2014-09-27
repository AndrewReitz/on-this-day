package com.andrewreitz.onthisday.ui.musiclist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.ui.misc.BetterViewAnimator;
import com.andrewreitz.onthisday.ui.misc.InfiniteScrollListener;
import java.util.List;
import javax.inject.Inject;
import mortar.Mortar;
import rx.functions.Action2;

import static com.andrewreitz.onthisday.ui.musiclist.MusicList.Presenter;

public class MusicListView extends BetterViewAnimator {
  @Inject Presenter presenter;

  @InjectView(R.id.music_list) ListView musicListView;

  private final MusicListAdapter adapter;

  public MusicListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);

    adapter = new MusicListAdapter(context);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    presenter.takeView(this);
    musicListView.setAdapter(adapter);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenter.dropView(this);
  }

  public MusicListAdapter getShows() {
    return adapter;
  }

  /** Set a listener that will continually populate the shows list */
  public void setLoadMoreListener(final Action2<String, Integer> loadMoreListener) {
    musicListView.setOnScrollListener(new InfiniteScrollListener(25) {
      @Override public void loadMore(int page, int totalItemsCount) {
        final Data item = adapter.getItem(totalItemsCount - 1);
        loadMoreListener.call(item.getName(), page);
      }
    });
  }

  public void hideSpinner() {
    setDisplayedChildId(R.id.music_list);
  }
}
