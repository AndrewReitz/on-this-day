package com.andrewreitz.onthisday.data;

import com.andrewreitz.onthisday.data.rx.EndObserver;
import java.util.LinkedHashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

abstract class Repository<K, V> {

  private final Cache<K, V> cache;
  private final Map<K, PublishSubject<V>> requests = new LinkedHashMap<K, PublishSubject<V>>();

  public Repository(Cache<K, V> cache) {
    if (cache == null) {
      throw new IllegalArgumentException("cache == null");
    }
    this.cache = cache;
  }

  public Subscription load(final K key, Observer<V> observer) {
    V item = cache.get(key);
    if (item != null) {
      // We have a cached value. Emit it immediately.
      observer.onNext(item);
    }

    PublishSubject<V> request = requests.get(key);
    if (request != null) {
      // There's an in-flight network request for this section already. Join it.
      return request.subscribe(observer);
    }

    request = PublishSubject.create();
    requests.put(key, request);

    Subscription subscription = request.subscribe(observer);

    request.subscribe(new EndObserver<V>() {
      @Override public void onEnd() {
        requests.remove(key);
      }

      @Override public void onNext(V item) {
        cache.put(key, item);
      }
    });

    doNetworkRequest(key, request);

    return subscription;
  }

  protected abstract void doNetworkRequest(K key, PublishSubject<V> publishSubject);

  public interface Cache<K, V> {
    V put(K key, V value);

    V get(K key);
  }
}
