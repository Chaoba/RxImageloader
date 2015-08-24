package cn.com.chaoba.rximageloader;

import android.content.Context;

import cn.com.chaoba.rximageloader.cacheobservers.DiskCacheObservable;
import cn.com.chaoba.rximageloader.cacheobservers.MemoryCacheOvservable;
import cn.com.chaoba.rximageloader.cacheobservers.NetCacheObservable;
import rx.Observable;

public class Sources {
    Context mContext;
    MemoryCacheOvservable mMemoryCacheOvservable;
    DiskCacheObservable mDiskCacheObservable;
    NetCacheObservable mNetCacheObservable;

    public Sources(Context mContext) {
        this.mContext = mContext;
        mMemoryCacheOvservable = new MemoryCacheOvservable();
        mDiskCacheObservable = new DiskCacheObservable(mContext);
        mNetCacheObservable = new NetCacheObservable();
    }


    public Observable<Data> memory(String url) {
        return mMemoryCacheOvservable.getObservable(url)
                .compose(logSource("MEMORY"));
    }

    public Observable<Data> disk(String url) {
        return mDiskCacheObservable.getObservable(url)
                .filter(data -> data.bitmap != null)
                .doOnNext(mMemoryCacheOvservable::putData)
                .compose(logSource("DISK"));

    }

    public Observable<Data> network(String url) {
        return mNetCacheObservable.getObservable(url)
                .doOnNext(data -> {
                    mMemoryCacheOvservable.putData(data);
                    mDiskCacheObservable.putData(data);
                })
                .compose(logSource("NET"));
    }

    Observable.Transformer<Data, Data> logSource(final String source) {
        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data != null && data.bitmap != null) {
                Logger.d(source + " has the data you are looking for!");
            } else {
                Logger.d(source + " not has the data!");
            }
        });
    }
}
