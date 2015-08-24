package cn.com.chaoba.rximageloader.cacheobservers;

import cn.com.chaoba.rximageloader.Data;
import cn.com.chaoba.rximageloader.MemoryCache;
import rx.Observable;

/**
 * Created by Liyanshun on 2015/8/24.
 */
public class MemoryCacheOvservable extends CacheObservable {
    public static final int DEFAULT_CACHE_SIZE = (24 /* MiB */ * 1024 * 1024);
    MemoryCache<String> mCache = new MemoryCache<String>(DEFAULT_CACHE_SIZE);

    @Override
    public Observable<Data> getObservable(String url) {
        Observable<Data> observable = Observable.create(subscriber -> {
            subscriber.onNext(new Data(mCache.get(url), url));
            subscriber.onCompleted();
        });
        return observable;
    }

    public void putData(Data data) {
        mCache.put(data.url, data.bitmap);
    }


}
