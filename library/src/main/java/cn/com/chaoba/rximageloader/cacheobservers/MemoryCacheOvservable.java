package cn.com.chaoba.rximageloader.cacheobservers;

import cn.com.chaoba.rximageloader.Data;
import cn.com.chaoba.rximageloader.Logger;
import cn.com.chaoba.rximageloader.MemoryCache;
import rx.Observable;
import rx.functions.Func1;

/**
 * MemoryCacheOvservable load data from memory
 * Created by Liyanshun on 2015/8/24.
 */
public class MemoryCacheOvservable extends CacheObservable {
    public static final int DEFAULT_CACHE_SIZE = (24 /* MiB */ * 1024 * 1024);
    MemoryCache<String> mCache = new MemoryCache<>(DEFAULT_CACHE_SIZE);

    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.just(url)
                .map(new Func1<String, Data>() {
                    @Override
                    public Data call(String s) {
                        Logger.i("search in memory");
                        return new Data(mCache.get(url), url);
                    }
                });
    }

    public void putData(Data data) {
        mCache.put(data.url, data.bitmap);
    }


}
