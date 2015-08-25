package cn.com.chaoba.rximageloader.cacheobservers;

import cn.com.chaoba.rximageloader.Data;
import rx.Observable;

/**
 * Created by Liyanshun on 2015/8/24.
 */
public abstract class CacheObservable {
    public abstract Observable<Data> getObservable(String url);
}
