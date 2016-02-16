package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.RedditArchivePair;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.velcro.betterviewanimator.BetterViewAnimator;
import com.andrewreitz.velcro.infinitescroll.InfiniteScrollListener;
import hugo.weaving.DebugLog;
import java.util.List;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Action2;

@DebugLog
public final class ShowListView extends BetterViewAnimator {
  /** The number of shows to pre-load in the list view. */
  private static final int PRE_LOAD_SHOWS = 25;

  @InjectView(R.id.music_list) ListView musicListView;

  private Action1<Integer> showClickAction;

  private final ShowListAdapter adapter;

  public ShowListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    adapter = new ShowListAdapter(context);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    musicListView.setAdapter(adapter);
  }

  @OnItemClick(R.id.music_list) void onShowClicked(int position) {
    showClickAction.call(position);
  }

  /** Add shows to this list. */
  public void addShows(List<RedditArchivePair> shows) {
    adapter.add(shows);
  }

  public RedditArchivePair getShowAtPosition(int itemPosition) {
    return adapter.getItem(itemPosition);
  }

  /** Set the action that should be taken when a show is clicked. */
  public void onShowClicked(Action1<Integer> clickAction) {
    this.showClickAction = clickAction;
  }

  /** Set a listener that will continually populate the shows list */
  public void setLoadMoreListener(final Action2<String, Integer> loadMoreListener) {
    musicListView.setOnScrollListener(new InfiniteScrollListener(PRE_LOAD_SHOWS) {
      @Override public void loadMore(int page, int totalItemsCount) {
        final RedditArchivePair item = adapter.getItem(totalItemsCount - 1);
        loadMoreListener.call(item.getRedditData().getName(), page);
      }
    });
  }

  public void hideSpinner() {
    setDisplayedChildId(R.id.music_list);
  }
}
