package com.andrewreitz.onthisday.data.api;

import com.andrewreitz.onthisday.data.api.model.Reddit;

import retrofit.http.GET;
import rx.Observable;

public interface RedditService {
  @GET("/r/gratefuldead.json") Observable<Reddit> getPosts();
}
