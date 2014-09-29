package com.andrewreitz.onthisday.data.api.archive;

import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import rx.Observable;

public interface ArchiveService {
  @GET("/{path}&output=json") Observable<Archive> getShowData(@EncodedPath("path") String path);
}
