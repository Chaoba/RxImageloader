package cn.com.chaoba.rximageloader;

import android.content.Context;

import cn.com.chaoba.rximageloader.cacheobservers.DiskCacheObservable;
import cn.com.chaoba.rximageloader.cacheobservers.MemoryCacheObservable;
import cn.com.chaoba.rximageloader.cacheobservers.NetCacheObservable;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Action1;
import rx.functions.Func1;

public class Sources {
    Context mContext;
    MemoryCacheObservable mMemoryCacheOvservable;
    DiskCacheObservable mDiskCacheObservable;
    NetCacheObservable mNetCacheObservable;

    public Sources(Context mContext) {
        this.mContext = mContext;
        mMemoryCacheOvservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskCacheObservable(mContext);
        mNetCacheObservable = new NetCacheObservable();
    }


    public Observable<Data> memory(String url) {
        return mMemoryCacheOvservable.getObservable(url)
                .compose(logSource("MEMORY"));
    }

    public Observable<Data> disk(String url) {
        return mDiskCacheObservable.getObservable(url)
                .compose(logSource("DISK"))
                .filter(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data.isAvailable();
                    }
                })
                //save picture to disk
                .doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        mMemoryCacheOvservable.putData(data);
                    }
                });
    }

    public Observable<Data> network(String url) {
        return mNetCacheObservable.getObservable(url)
                .compose(logSource("NET"))
                .filter(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data.isAvailable();
                    }
                })
                .doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        //save picture to disk and memory
                        mMemoryCacheOvservable.putData(data);
                        mDiskCacheObservable.putData(data);
                    }
                });
    }

    Transformer<Data, Data> logSource(final String source) {
        return new Transformer<Data, Data>() {
            @Override
            public Observable<Data> call(Observable<Data> dataObservable) {
                return dataObservable.doOnNext(
                        new Action1<Data>() {
                            @Override
                            public void call(Data data) {
                                if (data != null && data.isAvailable()) {
                                    Logger.i(source + " has the data!");
                                } else {
                                    Logger.i(source + " not has the data!");
                                }
                            }
                        });
            }
        };
    }
}
