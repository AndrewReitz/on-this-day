package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.screen.ShowListPresenter;
import com.andrewreitz.velcro.betterviewanimator.BetterViewAnimator;
import com.andrewreitz.velcro.infinitescroll.InfiniteScrollListener;
import javax.inject.Inject;
import mortar.Mortar;
import rx.functions.Action2;

public class ShowListView extends BetterViewAnimator {
  /** The number of shows to pre-load in the list view */
  private static final int PRE_LOAD_SHOWS = 25;

  @Inject ShowListPresenter presenter;

  @InjectView(R.id.music_list) ListView musicListView;

  private final ShowListAdapter adapter;

  public ShowListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);

    adapter = new ShowListAdapter(context);
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

  @Override protected void onWindowVisibilityChanged(int visibility) {
    super.onWindowVisibilityChanged(visibility);
    presenter.visibilityChanged(visibility == VISIBLE);
  }

  @OnItemClick(R.id.music_list) void onShowClicked(int position) {
    presenter.onShowSelected(adapter.getItem(position));
  }

  public ShowListAdapter getShows() {
    return adapter;
  }

  /** Set a listener that will continually populate the shows list */
  public void setLoadMoreListener(final Action2<String, Integer> loadMoreListener) {
    musicListView.setOnScrollListener(new InfiniteScrollListener(PRE_LOAD_SHOWS) {
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
