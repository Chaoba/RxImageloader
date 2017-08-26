package cn.com.chaoba.rximageloader.cacheobservers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.com.chaoba.rximageloader.Data;
import cn.com.chaoba.rximageloader.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * NetCacheObservable load data from intenet
 * Created by Liyanshun on 2015/8/24.
 */
public class NetCacheObservable extends CacheObservable {
    @Override
    public Observable<Data> getObservable(String url) {
        return Observable.just(url)
                .map(new Func1<String, Data>() {
                    @Override
                    public Data call(String url) {
                        Bitmap bitmap = null;
                        InputStream inputStream = null;
                        Logger.i("get img on net:" + url);
                        try {
                            URLConnection con = new URL(url)
                                    .openConnection();
                            inputStream = con.getInputStream();
                            bitmap = BitmapFactory
                                    .decodeStream(inputStream);
                        } catch (IOException e) {
                            throw Exceptions.propagate(e);
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    throw Exceptions.propagate(e);
                                }
                            }
                        }
                        return new Data(bitmap, url);
                    }
                }).subscribeOn(Schedulers.io());
    }
}
