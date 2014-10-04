package com.andrewreitz.onthisday.data.api.reddit;

import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Retrofit Service for interacting with the grateful dead subreddit. Specifically getting looking
 * for posts with on this day.
 */
public interface OnThisDayRedditService {
  @GET("/r/gratefuldead/search.json?q=On+this+day%3A&restrict_sr=on&sort=new&t=all")
  Observable<Reddit> getPosts();

  @GET("/r/gratefuldead/search.json?q=On+this+day%3A&restrict_sr=on&sort=new&t=all")
  Observable<Reddit> getPosts(@Query("after") String fullName, @Query("count") int count);
}
