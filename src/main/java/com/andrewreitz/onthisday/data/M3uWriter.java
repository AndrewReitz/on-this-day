package com.andrewreitz.onthisday.data;

import android.support.annotation.NonNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

public class M3uWriter {
  private static final String EXTENSION = "m3u";
  private static final String PREFIX = "on_this_day";

  private final File outputDir;

  public M3uWriter(@NonNull File outputDir) {
    this.outputDir = outputDir;
  }

  public Observable<File> createM3uFile(@NonNull String playlistName, List<String> files)
      throws IOException {
    return Observable.create((Subscriber<? super File> subscriber) -> {
      try {
        final File m3uFile =
            new File(outputDir, String.format("%s_%s.%s", PREFIX, playlistName, EXTENSION));
        final FileWriter fileWriter = new FileWriter(m3uFile);
        final PrintWriter printWriter = new PrintWriter(fileWriter);

        for (String file : files) {
          printWriter.println(file);
        }

        printWriter.flush();
        printWriter.close();

        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(m3uFile);
          subscriber.onCompleted();
        }
      } catch (IOException e) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onError(e);
        }
      }
    });
  }
}
