package com.andrewreitz.onthisday.ui.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.ArchiveRepository;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.data.rx.EndlessObserver;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;
import com.andrewreitz.onthisday.ui.showdetails.ShowDetailsView;
import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Layout;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.Blueprint;
import mortar.ViewPresenter;

@Layout(R.layout.view_show_details)
public class ShowDetailScreen implements HasParent<ShowsScreen>, Blueprint {
  private final Data show;

  public ShowDetailScreen(Context context, ActionBarOwner actionBarOwner, @NonNull Data show) {
    this.show = show;

    actionBarOwner.setConfig(
        new ActionBarOwner.Config(true, true, false, context.getString(R.string.app_name),
            new ActionBarOwner.MenuAction(R.drawable.ic_favorite,
                context.getString(R.string.favorite_show),
                () -> Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()),
            new ActionBarOwner.MenuAction(R.drawable.ic_play,
                context.getString(R.string.play_show) ,
                () -> Toast.makeText(context, "Test", Toast.LENGTH_LONG).show())));
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
    private final Flow flow;
    private final ArchiveRepository archiveRepository;
    private final Data show;

    @Inject public Presenter(@MainScope Flow flow, ArchiveRepository archiveRepository, Data show) {
      this.flow = flow;
      this.archiveRepository = archiveRepository;
      this.show = show;
    }

    @Override protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);

      final String showUrl = show.getUrl().replace("https://archive.org/", "");
      archiveRepository.loadShow(showUrl, new EndlessObserver<Archive>() {
        @Override public void onNext(Archive archive) {
          getView().bindTo(archive);
        }
      });
    }
  }
}
