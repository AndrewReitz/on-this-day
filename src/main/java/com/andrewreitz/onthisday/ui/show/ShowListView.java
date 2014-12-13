package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.velcro.betterviewanimator.BetterViewAnimator;
import com.andrewreitz.velcro.infinitescroll.InfiniteScrollListener;
import hugo.weaving.DebugLog;
import java.util.List;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action2;

public final class ShowListView extends BetterViewAnimator {
  /** Loads data into this list view for infinite scrolling abilities. */
  public interface DataLoader {
    /** Initial load. */
    Subscription loadData(Observer<List<Data>> observer);

    /** Load more. */
    Subscription loadMoreData(String name, int page, Observer<List<Data>> observer);
  }

  /** The number of shows to pre-load in the list view */
  private static final int PRE_LOAD_SHOWS = 25;

  @InjectView(R.id.music_list) ListView musicListView;

  private final ShowListAdapter adapter;

  public ShowListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    adapter = new ShowListAdapter(context);
  }

  @DebugLog @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    musicListView.setAdapter(adapter);
  }

  @DebugLog @OnItemClick(R.id.music_list) void onShowClicked(int position) {
  }

  @DebugLog
  public ShowListAdapter getShows() {
    return adapter;
  }

  /** Set a listener that will continually populate the shows list */
  @DebugLog
  public void setLoadMoreListener(final Action2<String, Integer> loadMoreListener) {
    musicListView.setOnScrollListener(new InfiniteScrollListener(PRE_LOAD_SHOWS) {
      @Override public void loadMore(int page, int totalItemsCount) {
        final Data item = adapter.getItem(totalItemsCount - 1);
        loadMoreListener.call(item.getName(), page);
      }
    });
  }

  @DebugLog
  public void hideSpinner() {
    setDisplayedChildId(R.id.music_list);
  }
}
