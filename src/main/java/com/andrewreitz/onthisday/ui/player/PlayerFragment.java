package com.andrewreitz.onthisday.ui.player;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.squareup.picasso.Picasso;
import icepick.Icicle;
import javax.inject.Inject;

public final class PlayerFragment extends Fragment {
  @Inject Picasso picasso;

  @Icicle Archive archive;

  public static PlayerFragment newInstance(Archive archive) {
    PlayerFragment playerFragment = new PlayerFragment();
    playerFragment.archive = archive;
    return playerFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (archive == null) {
      throw new IllegalStateException(
          "Archive is null, ensure that this fragment is created with newInstance.");
    }
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    PlayerView playerView = (PlayerView) inflater.inflate(R.layout.player_view, container, false);
    playerView.bindTo(picasso, archive);

    // SimpleMusicPlayerService.startService(app, archive);
    // Post event to change actionbar to "Music Player"

    return playerView;
  }
}
