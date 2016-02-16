package com.andrewreitz.onthisday.common;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public final class FileDownloader {
  private final OkHttpClient okHttpClient;

  @Inject public FileDownloader(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
  }

  /**
   * Download a file from the network.
   *
   * @param fileUrl The full url to the asset that will be downloaded.
   * @return Observable that when subscribed to will pass the result of the network request in
   * {@link rx.Observer#onNext}. The file can then be retrieved from
   * {@link com.squareup.okhttp.Response#body()}. If any errors do occur the will be passed to
   * {@link rx.Observer#onError}
   * @throws java.lang.NullPointerException if fileUrl is null.
   */
  public Observable<Response> downloadFile(final String fileUrl) {
    checkNotNull(fileUrl, "File url may not be null.");

    return Observable.create(new Observable.OnSubscribe<Response>() {
      @Override public void call(Subscriber<? super Response> subscriber) {
        Request request = new Request.Builder().url(fileUrl).build();

        try {
          Response response = okHttpClient.newCall(request).execute();
          if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(response);
          }
        } catch (IOException e) {
          if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
          }
        }
      }
    });
  }
}

