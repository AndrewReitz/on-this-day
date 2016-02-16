package com.andrewreitz.onthisday.ui.showdetails;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import icepick.Icicle;

public final class ShowDetailsFragment extends Fragment {

  @Icicle Archive show;

  public static ShowDetailsFragment newInstance(Archive show) {
    ShowDetailsFragment showDetailsFragment = new ShowDetailsFragment();
    showDetailsFragment.show = show;
    return showDetailsFragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    ShowDetailsView showDetailsView =
        (ShowDetailsView) inflater.inflate(R.layout.show_details_view, container, false);
    showDetailsView.bindTo(show);

    // TODO
    // Show app.getString(R.string.app_name)
    // and R.drawable.ic_play, app.getString(R.string.play_show in the actionbar
    // and stars ability

    return showDetailsView;
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
  //    final String title = Strings.toCsv(archive.getMetadata().getTitle());
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
