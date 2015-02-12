package com.andrewreitz.onthisday.data.api.archive;

import com.andrewreitz.onthisday.data.api.archive.model.Archive;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ArchiveService {
  @GET("/{path}&output=json") Observable<Archive> getShowData(
      @Path(value = "path", encode = false) String path);
}
