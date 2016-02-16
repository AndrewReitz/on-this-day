package com.andrewreitz.onthisday.ui.showdetails;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;
import com.andrewreitz.onthisday.data.api.archive.model.Metadata;
import com.andrewreitz.onthisday.util.Strings;
import com.andrewreitz.velcro.betterviewanimator.BetterViewAnimator;
import rx.functions.Action1;
import rx.functions.Func1;

public class ShowDetailsView extends BetterViewAnimator {
  @InjectView(R.id.show_details_content) ViewGroup content;
  @InjectView(R.id.show_details_title) TextView title;
  @InjectView(R.id.show_details_date) TextView date;
  @InjectView(R.id.show_details_venue_and_location) TextView venueAndLocation;

  public ShowDetailsView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void bindTo(Archive show) {
    final Metadata metadata = show.getMetadata();
    title.setText(Strings.toCsv(metadata.getTitle()));
    date.setText(Strings.toCsv(metadata.getDate()));
    venueAndLocation.setText(String.format("%s, %s", Strings.toCsv(metadata.getVenue()),
        Strings.toCsv(metadata.getCoverage())));

    show.getFileData().filter(new Func1<FileData, Boolean>() {
      @Override public Boolean call(FileData fileData) {
        return "original".equals(fileData.getSource());
      }
    }).filter(new Func1<FileData, Boolean>() {
      @Override public Boolean call(FileData fileData) {
        return fileData.getTitle() != null;
      }
    }).subscribe(new Action1<FileData>() {
      @Override public void call(FileData fileData) {
        TrackView trackView = new TrackView(getContext());
        trackView.bindTo(fileData);
        content.addView(trackView);
      }
    });

    setDisplayedChildId(R.id.show_details_scroll);
  }
}
