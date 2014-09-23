package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.api.RedditService;
import com.andrewreitz.onthisday.data.api.model.Child;
import com.andrewreitz.onthisday.data.api.model.Data;
import com.andrewreitz.onthisday.data.api.model.Listing;
import com.andrewreitz.onthisday.data.api.model.Reddit;
import com.google.common.collect.Maps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

@Singleton
public class RedditRepository {
  private final RedditService redditService;

  private final Map<String, List<Child>> redditCache = Maps.newLinkedHashMap();
  private final Map<String, ReplaySubject<List<Child>>> redditRequests = Maps.newLinkedHashMap();

  @Inject RedditRepository(RedditService redditService) {
    this.redditService = redditService;
  }

  public Observable<Data> loadReddit() {
    return redditService.getPosts()
        .map(Reddit::getData)
        .flatMap(listing -> Observable.from(listing.getChildren()))
        .map(Child::getData)
        .filter(data -> data.getTitle().startsWith("On this day:"));

//        redditService.getPosts()
//        .map(Reddit::getData)
//        .map(listing -> {
//          List<Child> children = Observable.from(listing.getChildren())
//              .filter(child -> child.getData().getTitle().startsWith("On this day:"))
//              .toList()
//              .toBlocking()
//              .first();
//
//          final int[] size = { children.size() };
//
//          List<Observable<Child>> observables = new LinkedList<>();
//          observables.add(Observable.from(children));
//          for (int i = 25; size[0] < 5; i += 25) {
//            Observable<Child> childObservable = observables.get(observables.size() - 1);
//            Child last = childObservable
//                .doOnNext(child -> size[0]++)
//                .takeLast(1)
//                .toBlocking()
//                .first();
//
//            Observable<Child> moreChildren = redditService.getPosts(last.getData().getName(), i)
//                .flatMap(reddit -> Observable.from(reddit.getData().getChildren()))
//                .filter(child -> child.getData().getTitle().startsWith("On this day:"));
//
//            observables.add(moreChildren);
//          }
//
//          return observables;
//
//        })
//        .flatMap(Observable::from)
//        .flatMap(childObservable -> childObservable)
//        .map(Child::getData);
  }
}
