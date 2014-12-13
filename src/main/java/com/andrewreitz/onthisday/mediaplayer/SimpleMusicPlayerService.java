package com.andrewreitz.onthisday.mediaplayer;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.andrewreitz.onthisday.OnThisDayApp;
import com.andrewreitz.onthisday.R;
import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import com.andrewreitz.onthisday.data.api.archive.model.FileData;
import com.andrewreitz.onthisday.mediaplayer.event.NextEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PauseEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PlayEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PlayStateEvent;
import com.andrewreitz.onthisday.mediaplayer.event.PreviousEvent;
import com.andrewreitz.onthisday.mediaplayer.event.StopServiceEvent;
import com.andrewreitz.onthisday.mediaplayer.event.TitleChangeEvent;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SimpleMusicPlayerService extends Service {
  private static final int ONGOING_NOTIFICATION_ID = 1972;
  private static final String MUSIC_ARCHIVE = "com.andrewreitz.onthisday.archive";

  /** Archive containing the music to play */
  private Archive archive;

  /** Play list of files and there contained data */
  private final List<Map.Entry<String, FileData>> files = Lists.newArrayList();

  /** Place in the playlist */
  private int fileIndex = 0;

  // Binder given to clients
  private final IBinder mBinder = new SimpleMusicPlayerBinder();

  @Inject Bus bus;
  @Inject SimpleMusicPlayer musicPlayer;

  public static void startService(@NonNull Context context, @NonNull Archive archive) {
    Intent intent = new Intent(context, SimpleMusicPlayerService.class);
    intent.putExtra(MUSIC_ARCHIVE, archive);
    context.startService(intent);
  }

  @Override public void onCreate() {
    super.onCreate();
    bus.register(this);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    final RemoteViews notificationTemplate =
        new RemoteViews(getPackageName(), R.layout.notification_template_base);

    final Notification notification = new NotificationCompat.Builder(this) //
        .setSmallIcon(R.drawable.ic_launcher) //
        .setContentTitle("On This Day") //
        .setContentText("Playing Music") //
        .setContent(notificationTemplate) //
        .build();

    startForeground(ONGOING_NOTIFICATION_ID, notification);

    archive = (Archive) intent.getSerializableExtra(MUSIC_ARCHIVE);

    musicPlayer.setOnCompletionListener(new Action0() {
      @Override public void call() {
        playCurrentSelection(Actions.empty());
      }
    });

    archive.getFiles().filter(new Func1<Map.Entry<String, FileData>, Boolean>() {
      @Override public Boolean call(Map.Entry<String, FileData> entry) {
        return entry.getValue().getFormat().equals("VBR MP3"); // TODO Make enum
      }
    }).toList().subscribe(new Action1<List<Map.Entry<String, FileData>>>() {
      @Override public void call(List<Map.Entry<String, FileData>> entries) {
        files.addAll(entries);
      }
    });

    playCurrentSelection(Actions.empty());

    return START_STICKY;
  }

  @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Subscribe public void onPlayEvent(PlayEvent event) {
    musicPlayer.play();
    bus.post(getTitleChangeEvent());
    bus.post(new PlayStateEvent(true));
  }

  @Subscribe public void onPauseEvent(PauseEvent event) {
    musicPlayer.pause();
    bus.post(new PlayStateEvent(false));
  }

  @Subscribe public void onPreviousEvent(PreviousEvent event) {
    fileIndex--;
    newPlayEvent();
  }

  @Subscribe public void onNextEvent(NextEvent event) {
    fileIndex++;
    newPlayEvent();
  }

  @Subscribe public void onStopServiceEvent(StopServiceEvent event) {
    this.stopSelf();
  }

  @Produce public PlayStateEvent produceAnswer() {
    return new PlayStateEvent(musicPlayer.isPlaying());
  }

  @Produce public TitleChangeEvent produceTitle() {
    return getTitleChangeEvent();
  }

  private void newPlayEvent() {
    playCurrentSelection(new Action0() {
      @Override public void call() {
        musicPlayer.play();
        bus.post(getTitleChangeEvent());
        bus.post(new PlayStateEvent(true));
      }
    });
  }

  private TitleChangeEvent getTitleChangeEvent() {
    if (files.isEmpty()) {
      return new TitleChangeEvent("");
    }
    FileData data = files.get(fileIndex).getValue();
    return new TitleChangeEvent(data.getTitle());
  }

  /** Go to the next song in the queue */
  private void playCurrentSelection(Action0 action) {
    final Optional<Uri> url = getNextUrl();
    if (url.isPresent()) {
      Action1<Throwable> errorAction = Actions.empty();
      musicPlayer.playFile(url.get())
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(Actions.empty(), errorAction, action);
    } else {
      // No more songs to play shut down
      this.stopSelf();
    }
  }

  /** Return the url of the next file to play */
  private Optional<Uri> getNextUrl() {
    if (files.size() == 0 || fileIndex < 0) {
      return Optional.absent();
    }

    final Map.Entry<String, FileData> file = files.get(fileIndex);
    return Optional.of(archive.getArchiveUrl().buildUpon() //
        .appendEncodedPath(file.getKey()) //
        .build());
  }

  public class SimpleMusicPlayerBinder extends Binder {
    SimpleMusicPlayerService getService() {
      // Return this instance of LocalService so clients can call public methods
      return SimpleMusicPlayerService.this;
    }
  }
}
