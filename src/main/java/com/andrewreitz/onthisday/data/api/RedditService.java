package com.andrewreitz.onthisday.data.api;

import com.andrewreitz.onthisday.data.api.model.Reddit;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface RedditService {
  @GET("/r/gratefuldead.json") Observable<Reddit> getPosts();

  @GET("/r/gratefuldead.json") Observable<Reddit> getPosts(@Query("after") String fullName,
                                                           @Query("count") int count);
}
