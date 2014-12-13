package com.andrewreitz.onthisday.ui.show;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.squareup.otto.Bus;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import prism.framework.PrismFacade;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action2;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public final class ShowListFragment extends Fragment {
  @Inject Bus bus;
  @Inject RedditShowLoader dataLoader;

  private ShowListView showList;

  private Subscription request = Subscriptions.empty();

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    showList = (ShowListView) inflater.inflate(R.layout.show_list_view, container, false);
    return showList;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    PrismFacade.bootstrap(this);

    // post to actionbar R.string.app_name
  }

  @Override public void onResume() {
    super.onResume();
    final Observer<List<Data>> showObserver = new Observer<List<Data>>() {
      @Override public void onCompleted() {
        showList.hideSpinner();
      }

      @Override public void onError(Throwable throwable) {
        // TODO Show error screen.
        Timber.e(throwable, "Error loading shows");
      }

      @Override public void onNext(List<Data> shows) {
        showList.getShows().add(shows);
      }
    };

    request = dataLoader.loadData(showObserver);
    showList.setLoadMoreListener(new Action2<String, Integer>() {
      @Override public void call(String name, Integer page) {
        request = dataLoader.loadMoreData(name, page, showObserver);
      }
    });
  }

  @Override public void onPause() {
    super.onPause();
    request.unsubscribe();
  }
}
