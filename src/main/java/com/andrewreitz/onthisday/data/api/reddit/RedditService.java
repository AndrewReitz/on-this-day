package com.andrewreitz.onthisday.data.api.reddit;

import com.andrewreitz.onthisday.data.api.reddit.model.Reddit;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface RedditService {
  @GET("/r/gratefuldead/search.json?q=On+this+day%3A&restrict_sr=on&sort=new&t=all")
  Observable<Reddit> getPosts();

  @GET("/r/gratefuldead/search.json?q=On+this+day%3A&restrict_sr=on&sort=new&t=all")
  Observable<Reddit> getPosts(@Query("after") String fullName, @Query("count") int count);
}
