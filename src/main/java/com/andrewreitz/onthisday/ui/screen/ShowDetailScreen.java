package com.andrewreitz.onthisday.ui.screen;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.ArchiveRepository;
import com.andrewreitz.onthisday.data.M3uWriter;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.showdetails.ShowDetailsView;
import com.andrewreitz.velcro.rx.EndlessObserver;
import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Layout;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import shillelagh.Shillelagh;

@Layout(R.layout.show_details_view)
public class ShowDetailScreen implements HasParent<ShowsScreen>, Blueprint {
  private final Data show;

  public ShowDetailScreen(@NonNull Data show) {
    this.show = show;
  }

  @Override public String getMortarScopeName() {
    return toString();
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @Override public ShowsScreen getParent() {
    return new ShowsScreen();
  }

  @Override public String toString() {
    return "ShowDetailScreen{" //
        + "show=" + show + //
        '}';
  }

  @dagger.Module(
      injects = ShowDetailsView.class,
      addsTo = Main.Module.class,
      complete = false)
  public class Module {
    @Provides Data provideShow() {
      return show;
    }
  }

  @Singleton
  public static class Presenter extends ViewPresenter<ShowDetailsView> {
    private final Application app;
    private final ActionBarOwner actionBarOwner;
    private final Shillelagh shillelagh;
    private final M3uWriter m3uWriter;
    private final Flow flow;
    private final ArchiveRepository archiveRepository;
    private final Data show;
    private final String showUrl;

    private Subscription actionbarSubscription = Subscriptions.empty();

    @Inject public Presenter(Application app, ActionBarOwner actionBarOwner, Shillelagh shillelagh,
        M3uWriter m3uWriter, Flow flow, ArchiveRepository archiveRepository, Data show) {
      this.app = app;
      this.actionBarOwner = actionBarOwner;
      this.shillelagh = shillelagh;
      this.m3uWriter = m3uWriter;
      this.flow = flow;
      this.archiveRepository = archiveRepository;
      this.show = show;
      this.showUrl = show.getUrl().replace("https://archive.org/", "");
    }

    @Override protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
      archiveRepository.loadShow(showUrl, new EndlessObserver<Archive>() {
        @Override public void onNext(Archive archive) {
          getView().bindTo(archive);
        }
      });

      setupActionBarMenu();
    }

    @Override protected void onExitScope() {
      ensureStopped();
    }

    public void visibilityChanged(boolean visible) {
      if (!visible) {
        ensureStopped();
      }
    }

    private void ensureStopped() {
      actionbarSubscription.unsubscribe();
    }

    private void setupActionBarMenu() {
      actionbarSubscription = createStarMenuAction().subscribe(
          starActionMenu -> actionBarOwner.setConfig(
              new ActionBarOwner.Config(true, true, false, app.getString(R.string.app_name),
                  starActionMenu, createPlayMenuAction())));
    }

    private ActionBarOwner.MenuAction createPlayMenuAction() {
      return new ActionBarOwner.MenuAction(R.drawable.ic_play, app.getString(R.string.play_show),
          () -> archiveRepository.loadShow(showUrl, new EndlessObserver<Archive>() {
            @Override public void onNext(Archive archive) {
              flow.goTo(new PlayerScreen(show, archive));
            }
          }));
    }

    // TODO Make this readable...
    private Observable<ActionBarOwner.MenuAction> createStarMenuAction() {
      return Observable.create(
          (Subscriber<? super ActionBarOwner.MenuAction> subscriber) -> shillelagh.get(Data.class)
              .contains(show)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(contains -> {
                if (!subscriber.isUnsubscribed()) {
                  final String title = app.getString(R.string.favorite_show);
                  if (!contains) {
                    // This double check might not be necessary.
                    subscriber.onNext(new ActionBarOwner.MenuAction(R.drawable.ic_favorite, title,
                        () -> shillelagh.get(Data.class)
                            .contains(show)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(c -> {
                              if (!c) {
                                shillelagh.insert(show);
                                Toast.makeText(app, app.getString(R.string.saved_exclamation),
                                    Toast.LENGTH_LONG).show();

                                // Force a refresh
                                setupActionBarMenu();
                              }
                            })));
                  } else {
                    subscriber.onNext(
                        new ActionBarOwner.MenuAction(R.drawable.ic_favorited, title, () -> {
                          shillelagh.delete(show);
                          Toast.makeText(app, app.getString(R.string.removed_exclemation),
                              Toast.LENGTH_LONG).show();

                          // Force a refresh
                          setupActionBarMenu();
                        }));
                  }
                  subscriber.onCompleted();
                }
              }));
    }

    // Add back in with specific button since this doesn't work with most players.
    // Also look for .m3u's first many shows have them.
    //private void createM3uFileAndShare(Archive archive) {
    //  final String filePath =
    //      String.format("http://%s%s", archive.getServer(), archive.getDir());
    //  final List<String> paths = Lists.newLinkedList();
    //
    //  archive.getFiles()
    //      .filter(s -> s.endsWith(".mp3"))
    //      .subscribe(fileName -> paths.add(filePath + fileName));
    //
    //  try {
    //    final String title = Strings.join(archive.getMetadata().getTitle());
    //    m3uWriter.createM3uFile(title, paths).subscribe(file -> {
    //      Intent intent = new Intent();
    //      intent.setAction(Intent.ACTION_VIEW);
    //      intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
    //      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //      app.startActivity(intent);
    //    });
    //  } catch (IOException e) {
    //    Timber.e(e, "Error creating m3u file");
    //  }
    //}
  }
}
