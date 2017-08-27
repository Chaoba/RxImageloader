package cn.com.chaoba.rximageloader.cacheobservers;

import cn.com.chaoba.rximageloader.Data;
import cn.com.chaoba.rximageloader.Logger;
import cn.com.chaoba.rximageloader.MemoryCache;
import rx.Observable;
import rx.functions.Func1;

/**
 * MemoryCacheObservable load data from memory
 * Created by Liyanshun on 2015/8/24.
 */
public class MemoryCacheObservable extends CacheObservable {
    //Cache size 24MB
    public static final int CACHE_SIZE = (24 * 1024 * 1024);
    MemoryCache<String> mCache = new MemoryCache<>(CACHE_SIZE);

    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.just(url)
                .map(new Func1<String, Data>() {
                    @Override
                    public Data call(String url) {
                        Logger.i("search in memory");
                        return new Data(mCache.get(url), url);
                    }
                });
    }

    public void putData(Data data) {
        mCache.put(data.url, data.bitmap);
    }
}
