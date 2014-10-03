package com.andrewreitz.onthisday.ui.screen;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.ArchiveRepository;
import com.andrewreitz.onthisday.data.M3uWriter;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.motar.core.MainScope;
import com.andrewreitz.onthisday.ui.showdetails.ShowDetailsView;
import com.andrewreitz.velcro.rx.EndlessObserver;
import com.google.common.collect.Lists;
import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Layout;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.Blueprint;
import mortar.ViewPresenter;
import timber.log.Timber;

@Layout(R.layout.view_show_details)
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
    private final M3uWriter m3uWriter;
    private final Flow flow;
    private final ArchiveRepository archiveRepository;
    private final Data show;
    private final String showUrl;

    @Inject public Presenter(Application app, ActionBarOwner actionBarOwner, M3uWriter m3uWriter,
        @MainScope Flow flow, ArchiveRepository archiveRepository, Data show) {
      this.app = app;
      this.actionBarOwner = actionBarOwner;
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

    private void setupActionBarMenu() {
      actionBarOwner.setConfig(
          new ActionBarOwner.Config(true, true, false, app.getString(R.string.app_name),
              new ActionBarOwner.MenuAction(R.drawable.ic_favorite,
                  app.getString(R.string.favorite_show),
                  () -> Toast.makeText(app, "Saved!", Toast.LENGTH_LONG).show()),
              new ActionBarOwner.MenuAction(R.drawable.ic_play, app.getString(R.string.play_show),
                  () -> archiveRepository.loadShow(showUrl, new EndlessObserver<Archive>() {
                    @Override public void onNext(Archive archive) {
                      final String filePath =
                          String.format("http://%s%s", archive.getServer(), archive.getDir());
                      final List<String> paths = Lists.newLinkedList();

                      archive.getFiles()
                          .filter(s -> s.endsWith(".mp3"))
                          .subscribe(fileName -> paths.add(filePath + fileName));

                      try {
                        m3uWriter.createM3uFile(paths).subscribe(file -> {
                          Intent intent = new Intent();
                          intent.setAction(Intent.ACTION_VIEW);
                          intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          app.startActivity(intent);
                        });
                      } catch (IOException e) {
                        Timber.e(e, "Error creating m3u file");
                      }
                    }
                  }))));
    }
  }
}
