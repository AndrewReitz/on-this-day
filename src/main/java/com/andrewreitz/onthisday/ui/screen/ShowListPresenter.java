package com.andrewreitz.onthisday.ui.screen;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;
import com.andrewreitz.onthisday.ui.show.ShowListView;
import flow.Flow;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.ViewPresenter;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

@Singleton
public class ShowListPresenter extends ViewPresenter<ShowListView> {
  private final Flow flow;
  private final DataLoader dataLoader;
  private final Context context;
  private final ActionBarOwner actionBarOwner;

  private Subscription request = Subscriptions.empty();

  @Inject ShowListPresenter(Application context, ActionBarOwner actionBarOwner,
      @MainScope Flow flow, DataLoader dataLoader) {
    this.flow = flow;
    this.dataLoader = dataLoader;
    this.context = context;
    this.actionBarOwner = actionBarOwner;
  }

  @Override public void onLoad(Bundle savedInstanceState) {
    super.onLoad(savedInstanceState);

    actionBarOwner.setConfig(
        new ActionBarOwner.Config(true, true, true, context.getString(R.string.app_name)));

    final ShowListView view = getView();

    Observer<List<Data>> showObserver = new Observer<List<Data>>() {
      @Override public void onCompleted() {
        view.hideSpinner();
      }

      @Override public void onError(Throwable throwable) {
        // TODO Show error screen.
        Timber.e(throwable, "Error loading shows");
      }

      @Override public void onNext(List<Data> shows) {
        view.getShows().add(shows);
      }
    };

    request = dataLoader.loadData(showObserver);
    view.setLoadMoreListener(
        (name, page) -> request = dataLoader.loadMoreData(name, page, showObserver));
  }

  @Override public void onExitScope() {
    ensureStopped();
  }

  public void onShowSelected(Data show) {
    flow.goTo(new ShowDetailScreen(show));
  }

  public void visibilityChanged(boolean visible) {
    if (!visible) {
      ensureStopped();
    }
  }

  private void ensureStopped() {
    request.unsubscribe();
  }

  public interface DataLoader {
    Subscription loadData(Observer<List<Data>> observer);

    Subscription loadMoreData(String name, int page, Observer<List<Data>> observer);
  }
}
