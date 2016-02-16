package com.andrewreitz.onthisday.ui.show;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.RedditArchivePair;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;
import com.andrewreitz.onthisday.data.api.archive.model.Metadata;
import com.andrewreitz.onthisday.ui.common.MoreLessButton;
import com.andrewreitz.onthisday.ui.showdetails.TrackView;
import com.andrewreitz.onthisday.util.Strings;
import java.util.List;
import javax.inject.Inject;
import rx.functions.Action1;
import rx.functions.Func1;
import shillelagh.Shillelagh;

import static butterknife.ButterKnife.Action;

public final class ShowItemView extends FrameLayout {
  @InjectView(R.id.show_item_title) TextView title;
  @InjectView(R.id.show_item_venue_and_location) TextView venueAndLocation;
  @InjectView(R.id.show_item_set_list) ViewGroup setList;
  @InjectView(R.id.show_item_more) MoreLessButton moreLessButton;

  /** Views that should be visible or gone when the more/less button is pressed. */
  @InjectViews({ R.id.show_item_set_list, R.id.show_item_set_list_divider }) List<View>
      setListViews;

  @Inject Shillelagh shillelagh;

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

  /** Action for setting all views in a list to visible. */
  private static final Action<View> VISIBLE = new Action<View>() {
    @Override public void apply(View view, int index) {
      view.setVisibility(View.VISIBLE);
    }
  };

  /** Action for setting all views in a list to gone. */
  private static final Action<View> GONE = new Action<View>() {
    @Override public void apply(View view, int index) {
      view.setVisibility(View.GONE);
    }
  };

  private final Action1<FileData> addFileToSetList = new Action1<FileData>() {
    @Override public void call(FileData fileData) {
      TrackView trackView = new TrackView(getContext());
      trackView.bindTo(fileData);
      setList.addView(trackView);
    }
  };

  /** The data backing this view. */
  private RedditArchivePair pair;

  public ShowItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);
  }

  @OnClick(R.id.show_item_download) void onDownloadShow() {
    // TODO download show to sd card
  }

  @OnClick(R.id.show_item_play) void onPlayShow() {
    // TODO play music with player service (navigate to player?)
  }

  @OnClick(R.id.show_item_favorite) void onFavorited() {
    shillelagh.insert(pair);
  }

  public void bindTo(RedditArchivePair pair) {
    this.pair = pair;

    Archive archive = pair.getArchiveData();
    Metadata metadata = archive.getMetadata();
    title.setText(Strings.toCsv(metadata.getTitle()));
    venueAndLocation.setText(String.format("%s, %s", Strings.toCsv(metadata.getVenue()),
        Strings.toCsv(metadata.getCoverage())));

    archive.getFileData().filter(ORIGINAL_SOURCE_FILTER) //
        .filter(NULL_FILEDATE_FILTER) //
        .subscribe(addFileToSetList);

    moreLessButton.setMoreLessListener(new MoreLessButton.MoreLessListener() {
      @Override public void onMore() {
        ButterKnife.apply(setListViews, VISIBLE);
      }

      @Override public void onLess() {
        ButterKnife.apply(setListViews, GONE);
      }
    });
  }
}
