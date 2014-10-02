package com.andrewreitz.onthisday.ui.showdetails;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.Metadata;
import com.andrewreitz.onthisday.util.Strings;
import com.andrewreitz.velcro.betterviewanimator.BetterViewAnimator;
import javax.inject.Inject;
import mortar.Mortar;

import static com.andrewreitz.onthisday.ui.screen.ShowDetailScreen.Presenter;

public class ShowDetailsView extends BetterViewAnimator {
  @Inject Presenter presenter;

  @InjectView(R.id.show_details_content) ViewGroup content;
  @InjectView(R.id.show_details_title) TextView title;
  @InjectView(R.id.show_details_date) TextView date;
  @InjectView(R.id.show_details_venue_and_location) TextView venueAndLocation;

  public ShowDetailsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    Mortar.inject(context, this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
    presenter.takeView(this);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenter.dropView(this);
  }

  public void bindTo(Archive show) {
    final Metadata metadata = show.getMetadata();
    title.setText(Strings.join(metadata.getTitle()));
    date.setText(Strings.join(metadata.getDate()));
    venueAndLocation.setText(String.format("%s, %s", Strings.join(metadata.getVenue()),
        Strings.join(metadata.getCoverage())));

    show.getFileData()
        .filter(fileData -> "original".equals(fileData.getSource()))
        .filter(fileData -> fileData.getTitle() != null)
        .subscribe(fileData -> {
          final TrackView trackView = new TrackView(getContext());
          trackView.bindTo(fileData);
          content.addView(trackView);
        });

    setDisplayedChildId(R.id.show_details_scroll);
  }
}
