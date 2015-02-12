package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;
import com.andrewreitz.onthisday.data.api.archive.model.Metadata;
import com.andrewreitz.onthisday.ui.showdetails.TrackView;
import com.andrewreitz.onthisday.util.Strings;
import rx.functions.Action1;
import rx.functions.Func1;

public final class ShowItemView extends FrameLayout {
  @InjectView(R.id.show_item_title) TextView title;
  @InjectView(R.id.show_item_venue_and_location) TextView venueAndLocation;
  @InjectView(R.id.show_item_set_list) ViewGroup setList;

  /** Filter out all but the "original source" file data. */
  private static final Func1<FileData, Boolean> ORIGINAL_SOURCE_FILTER =
      new Func1<FileData, Boolean>() {
        @Override public Boolean call(FileData fileData) {
          return "original".equals(fileData.getSource());
        }
      };

  /** Filter out null fileDate. */
  private static final Func1<FileData, Boolean> NULL_FILEDATE_FILTER =
      new Func1<FileData, Boolean>() {
        @Override public Boolean call(FileData fileData) {
          return fileData.getTitle() != null;
        }
      };

  private final Action1<FileData> addFileToSetList = new Action1<FileData>() {
    @Override public void call(FileData fileData) {
      TrackView trackView = new TrackView(getContext());
      trackView.bindTo(fileData);
      setList.addView(trackView);
    }
  };

  public ShowItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  public void bindTo(Archive show) {
    Metadata metadata = show.getMetadata();
    title.setText(Strings.toCsv(metadata.getTitle()));
    venueAndLocation.setText(String.format("%s, %s", Strings.toCsv(metadata.getVenue()),
        Strings.toCsv(metadata.getCoverage())));

    show.getFileData().filter(ORIGINAL_SOURCE_FILTER) //
        .filter(NULL_FILEDATE_FILTER) //
        .subscribe(addFileToSetList);
  }
}
