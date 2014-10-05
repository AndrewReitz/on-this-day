package com.andrewreitz.onthisday.ui.screen;

import android.os.Bundle;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import com.andrewreitz.onthisday.ui.motar.android.ActionBarOwner;
import com.andrewreitz.onthisday.ui.motar.core.Main;
import com.andrewreitz.onthisday.ui.player.PlayerView;
import com.squareup.picasso.Picasso;
import dagger.Provides;
import flow.HasParent;
import flow.Layout;
import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.Blueprint;
import mortar.ViewPresenter;

@Layout(R.layout.player_view)
public class PlayerScreen implements HasParent<ShowDetailScreen>, Blueprint {
  private final Data data;
  private final Archive archive;

  public PlayerScreen(Data data, Archive archive) {
    this.data = data;
    this.archive = archive;
  }

  @Override public String getMortarScopeName() {
    return "PlayerScreen{" //
        + "archive=" + archive //
        + '}';
  }

  @Override public Object getDaggerModule() {
    return new Module();
  }

  @Override public ShowDetailScreen getParent() {
    return new ShowDetailScreen(data);
  }

  @dagger.Module(
      injects = PlayerView.class,
      addsTo = Main.Module.class,
      complete = false)
  public class Module {
    @Provides Archive provideArchive() {
      return archive;
    }
  }

  @Singleton
  public static class Presenter extends ViewPresenter<PlayerView> {
    private final Archive archive;
    private final Picasso picasso;
    private final ActionBarOwner actionBarOwner;

    @Inject public Presenter(ActionBarOwner actionBarOwner, Archive archive, Picasso picasso) {
      this.actionBarOwner = actionBarOwner;
      this.archive = archive;
      this.picasso = picasso;
    }

    @Override protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);

      actionBarOwner.setConfig(new ActionBarOwner.Config(true, true, false, "Music Player"));

      final PlayerView view = getView();
      view.bindTo(picasso, archive);
    }
  }
}
