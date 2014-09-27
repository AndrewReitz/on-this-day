package com.andrewreitz.onthisday.ui.misc;

import android.widget.AbsListView;

/** Scroll listener for infinite scrolling. */
public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
  private final int bufferItemCount;

  private int currentPage = 0;
  private int itemCount = 0;
  private boolean isLoading = true;

  public InfiniteScrollListener(int bufferItemCount) {
    this.bufferItemCount = bufferItemCount;
  }

  public abstract void loadMore(int page, int totalItemsCount);

  @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
    // Do Nothing
  }

  @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
      int totalItemCount) {
    if (totalItemCount < itemCount) {
      this.itemCount = totalItemCount;
      if (totalItemCount == 0) {
        this.isLoading = true;
      }
    }

    if (isLoading && (totalItemCount > itemCount)) {
      isLoading = false;
      itemCount = totalItemCount;
      currentPage++;
    }

    if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + bufferItemCount)) {
      loadMore(currentPage + 1, totalItemCount);
      isLoading = true;
    }
  }
}
