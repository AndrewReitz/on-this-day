package com.andrewreitz.onthisday.ui.showdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;

public class TrackView extends RelativeLayout {
  @InjectView(R.id.track_title) TextView textView;

  public TrackView(Context context) {
    super(context);
    LayoutInflater.from(context).inflate(R.layout.track_details_view, this, true);
    ButterKnife.inject(this);
  }

  public void bindTo(FileData fileData) {
    textView.setText(fileData.getTitle());
  }
}
